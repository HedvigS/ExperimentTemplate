/*
 * Copyright (C) 2014 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.ru.languageininteraction.language.client.presenter;

import nl.mpi.tg.eg.experiment.client.presenter.Presenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.Date;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.ru.languageininteraction.language.client.Messages;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.AudioEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.ru.languageininteraction.language.client.model.HighScoreData;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionException;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionListener;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
import nl.ru.languageininteraction.language.client.registration.GameDataSubmissionService;
import nl.ru.languageininteraction.language.client.service.ResultsSerialiser;
import nl.ru.languageininteraction.language.client.view.ScorePageView;
import nl.ru.languageininteraction.language.client.service.SocialMediaPost;

/**
 * @since Nov 26, 2014 4:12:27 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class ScorePagePresenter implements Presenter {

    protected final Messages messages = GWT.create(Messages.class);
    protected final RootLayoutPanel widgetTag;
    final AudioPlayer audioPlayer;
    final ScorePageView scorePageView;
    final UserResults userResults;
    private PresenterEventListner backEventListner = null;
    private PresenterEventListner nextEventListner = null;
    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();

    public ScorePagePresenter(RootLayoutPanel widgetTag, final AudioPlayer audioPlayer, UserResults userResults) throws AudioException {
        scorePageView = new ScorePageView(audioPlayer);
        this.audioPlayer = audioPlayer;
        this.widgetTag = widgetTag;
        this.userResults = userResults;
    }

    @Override
    public void setState(final AppEventListner appEventListner, final ApplicationState prevState, final ApplicationState nextState) {
        widgetTag.clear();
        if (prevState != null) {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button) {
                    audioPlayer.stopAll();
                    appEventListner.requestApplicationState(prevState);
                }

                @Override
                public String getLabel() {
                    return prevState.label;
                }
            };
        } else {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button) {
                    audioPlayer.stopAll();
                    appEventListner.requestApplicationState(ApplicationState.chooseplayer);
                }

                @Override
                public String getLabel() {
                    return ApplicationState.menu.label;
                }
            };
        }
        scorePageView.setEditUserListner(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button) {
                appEventListner.requestApplicationState(ApplicationState.playerdetails);
            }

            @Override
            public String getLabel() {
                return ApplicationState.playerdetails.label;
            }
        });
        scorePageView.setJustContinueListner(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button) {
                audioPlayer.stopAll();
//                userResults.getUserData().setMetadataValue(metadataFieldProvider.shareMetadataField, metadataFieldProvider.shareMetadataField.getControlledVocabulary()[1]);
                try {
                    userResults.getUserData().validateNameField();
                    new LocalStorage().storeData(userResults);
                    appEventListner.requestApplicationState(nextState);
                } catch (MetadataFieldException exception) {
                    // if the user has not entered their name etc then all actions force them to go to the edit details screen (which can only happen if they are playing for the first time or the clicked new player)
                    appEventListner.requestApplicationState(ApplicationState.playerdetails);
                }
            }

            @Override
            public String getLabel() {
                return nextState.label;
            }
        });
        scorePageView.setShareContinueListner(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button) {
                audioPlayer.stopAll();
//                userResults.getUserData().setMetadataValue(metadataFieldProvider.shareMetadataField, metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0]);
                try {
                    userResults.getUserData().validateNameField();
                    new LocalStorage().storeData(userResults);
                    String restultsData = URL.encodeQueryString(new ResultsSerialiser() {
                        final DateTimeFormat format = DateTimeFormat.getFormat(messages.reportDateFormat());

                        @Override
                        protected String formatDate(Date date) {
                            return format.format(date);
                        }
                    }.serialise(userResults));
                    new LocalStorage().addStoredGameData(userResults.getUserData().getUserId(), restultsData);
                    userResults.clearResults();
                    appEventListner.requestApplicationState(nextState);
                } catch (MetadataFieldException exception) {
                    // if the user has not entered their name etc then all actions force them to go to the edit details screen (which can only happen if they are playing for the first time or the clicked new player)
                    appEventListner.requestApplicationState(ApplicationState.playerdetails);
                }
            }

            @Override
            public String getLabel() {
                return nextState.label;
            }
        });
        scorePageView.setSocialMediaListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return nextState.label;
            }

            @Override
            public void eventFired(ButtonBase button) {
                audioPlayer.stopAll();
                final NumberFormat numberFormat2 = NumberFormat.getFormat("0");
                new SocialMediaPost().postImageAndLink(
                        messages.socialMediaPostText(
                                numberFormat2.format(userResults.getUserData().getGamesPlayed()),
                                numberFormat2.format(userResults.getUserData().getBestScore())),
                        messages.socialMediaPostSubject(),
                        messages.socialMediaPostImage(),
                        messages.socialMediaPostUrl());
            }
        });

        scorePageView.setupScreen(backEventListner, nextEventListner);
        audioPlayer.setOnEndedListener(new AudioEventListner() {

            @Override
            public void audioEnded() {
                scorePageView.showAudioEnded();
            }
        });
        widgetTag.add(scorePageView);
        final String userNameValue = userResults.getUserData().getMetadataValue(metadataFieldProvider.playernameMetadataField);
        scorePageView.setUserName((userNameValue.isEmpty()) ? messages.defaultUserName() : userNameValue);
        scorePageView.setUserScore(userResults.getUserData().getBestScore());
        scorePageView.setUserLevel(userResults.getGameData().getChoicesPerRound());
        scorePageView.setEndangeredCount(userResults.getGameData().getRoundsCorrectEndangered());
        scorePageView.setRoundsData(userResults.getGameData().getRoundsCorrect(), userResults.getGameData().getRoundsPlayed());

        // clear the highscores text
        scorePageView.clearHighScoreText();
        final GameDataSubmissionService registrationService = new GameDataSubmissionService();
        final boolean isShareData = metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0].equals(userResults.getUserData().getMetadataValue(metadataFieldProvider.shareMetadataField));
        registrationService.submitScores(isShareData, userResults, new DataSubmissionListener() {

            @Override
            public void scoreSubmissionFailed(DataSubmissionException exception) {
                // todo: store the serialised data for later upload
                switch (exception.getErrorType()) {
                    case buildererror:
                        appEventListner.requestApplicationState(ApplicationState.highscoresfailedbuildererror);
                        break;
                    case connectionerror:
                        appEventListner.requestApplicationState(ApplicationState.highscoresfailedconnectionerror);
                        break;
                    case non202response:
                        appEventListner.requestApplicationState(ApplicationState.highscoresfailednon202);
                        break;
                }
            }

            @Override
            public void scoreSubmissionComplete(JsArray<HighScoreData> highScoreData) {
                final HighScoreData highScoreJson = highScoreData.get(0);
                for (int index = 0; index < highScoreJson.getCount(); index++) {
                    scorePageView.setHighScore(index, highScoreJson.getPlayerName(index), highScoreJson.getPlayerHighScore(index));
                }
                if (isShareData) {
                    userResults.clearResults();
                    new LocalStorage().clearStoredGameData(userResults.getUserData().getUserId());
                }
                appEventListner.requestApplicationState(ApplicationState.highscoresubmitted);
            }
        }, messages.reportDateFormat());
    }

    @Override
    public void fireBackEvent() {
        if (backEventListner != null) {
            audioPlayer.stopAll();
            backEventListner.eventFired(null);
        }
    }

    @Override
    public void fireResizeEvent() {
        scorePageView.resizeView();
    }
}
