/*
 * Copyright (C) 2015 Language In Interaction
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
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.ru.languageininteraction.language.client.Messages;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
import nl.ru.languageininteraction.language.client.view.PlayerDetailsView;

/**
 * @since Feb 4, 2015 11:26:01 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class PlayerDetailsPresenter extends AbstractSvgPresenter implements Presenter {

    protected final Messages messages = GWT.create(Messages.class);
    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();
    private final PlayerDetailsView playerDetailsView;
    final AppEventListner appEventListner;

    public PlayerDetailsPresenter(RootLayoutPanel widgetTag, UserResults userResults, AudioPlayer audioPlayer, final AppEventListner appEventListner) throws AudioException {
        super(widgetTag, userResults, audioPlayer, new PlayerDetailsView(audioPlayer));
        playerDetailsView = ((PlayerDetailsView) abstractSvgView);
        this.appEventListner = appEventListner;
    }

    @Override
    void configureSvg() {
        String currentAge = userResults.getUserData().getMetadataValue(metadataFieldProvider.ageMetadataField);
        boolean shareAgreed = metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0].equals(userResults.getUserData().getMetadataValue(metadataFieldProvider.shareMetadataField));
        playerDetailsView.setShareData(shareAgreed);
        final String[] ageVocabulary = metadataFieldProvider.ageMetadataField.getControlledVocabulary();
        if (ageVocabulary[0].equals(currentAge)) {
            playerDetailsView.setAge1();
        } else if (ageVocabulary[1].equals(currentAge)) {
            playerDetailsView.setAge2();
        } else if (ageVocabulary[2].equals(currentAge)) {
            playerDetailsView.setAge3();
        } else {
            playerDetailsView.clearAge();
        }

        playerDetailsView.setAge1ButtonListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public void eventFired(ButtonBase button) {
                userResults.getUserData().setMetadataValue(metadataFieldProvider.ageMetadataField, metadataFieldProvider.ageMetadataField.getControlledVocabulary()[0]);
                playerDetailsView.setAge1();
            }
        });
        playerDetailsView.setAge2ButtonListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public void eventFired(ButtonBase button) {
                userResults.getUserData().setMetadataValue(metadataFieldProvider.ageMetadataField, metadataFieldProvider.ageMetadataField.getControlledVocabulary()[1]);
                playerDetailsView.setAge2();
            }
        });
        playerDetailsView.setAge3ButtonListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public void eventFired(ButtonBase button) {
                userResults.getUserData().setMetadataValue(metadataFieldProvider.ageMetadataField, metadataFieldProvider.ageMetadataField.getControlledVocabulary()[2]);
                playerDetailsView.setAge3();
            }
        });
        playerDetailsView.setShareCheckBoxListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public void eventFired(ButtonBase button) {
//                boolean shareAgreed = metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0].equals(userResults.getUserData().getMetadataValue(metadataFieldProvider.shareMetadataField));
//                if (shareAgreed) {
////                    userResults.getUserData().setMetadataValue(metadataFieldProvider.shareMetadataField, metadataFieldProvider.shareMetadataField.getControlledVocabulary()[1]);
//                    playerDetailsView.setShareData(Boolean.FALSE);
//                } else {
////                    userResults.getUserData().setMetadataValue(metadataFieldProvider.shareMetadataField, metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0]);
//                    playerDetailsView.setShareData(Boolean.TRUE);
//                }
                appEventListner.requestApplicationState(ApplicationState.stopSharing);
            }
        });
        playerDetailsView.setEditNameListner(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public void eventFired(ButtonBase button) {
                final TextBox userNameBox = new TextBox();
                userNameBox.setStylePrimaryName("popupTextBox");
                userNameBox.setValue(userResults.getUserData().getMetadataValue(metadataFieldProvider.playernameMetadataField));
                playerDetailsView.showWidgetPopup(new PresenterEventListner() {

                    @Override
                    public String getLabel() {
                        return "";
                    }

                    @Override
                    public void eventFired(ButtonBase button) {
                        userResults.getUserData().setMetadataValue(metadataFieldProvider.playernameMetadataField, userNameBox.getValue());
                        playerDetailsView.setUserNameField(userResults.getUserData().getMetadataValue(metadataFieldProvider.playernameMetadataField));
                    }
                }, userNameBox);
            }
        });
        final String userNameValue = userResults.getUserData().getMetadataValue(metadataFieldProvider.playernameMetadataField);
        playerDetailsView.setUserNameField((userNameValue.isEmpty()) ? messages.defaultUserName() : userNameValue);
        playerDetailsView.setUserScoreField(userResults.getUserData().getBestScore());
    }

    @Override
    boolean nextEventFired() {
        new LocalStorage().storeData(userResults);
        return true;
    }
}
