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
package nl.mpi.tg.eg.experiment.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.logging.Logger;
import nl.mpi.tg.eg.experiment.client.listener.AudioExceptionListner;
import nl.mpi.tg.eg.experiment.client.presenter.Presenter;
import nl.mpi.tg.eg.experiment.client.presenter.ErrorPresenter;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.model.UserId;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;

/**
 * @since Oct 7, 2014 11:07:35 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AppController implements AppEventListner, AudioExceptionListner {

    protected static final Logger logger = Logger.getLogger(AppController.class.getName());
    private final Version version = GWT.create(Version.class);
    final LocalStorage localStorage = new LocalStorage();
    final DataSubmissionService submissionService = new DataSubmissionService(localStorage);
    protected final RootLayoutPanel widgetTag;
    protected Presenter presenter;
    protected final UserResults userResults;
    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();

    public AppController(RootLayoutPanel widgetTag) {
        this.widgetTag = widgetTag;
        final UserId lastUserId = localStorage.getLastUserId();
        if (lastUserId == null) {
            userResults = new UserResults(new UserData());
            // we save the results here so that the newly created user id is preserved even if the user refreshes
            localStorage.storeData(userResults);
        } else {
            userResults = new UserResults(localStorage.getStoredData(lastUserId));
        }
    }

    protected void preventWindowClose(final String messageString) {

        // on page close, back etc. provide a warning that their session will be invalide and they will not be paid etc.
        Window.addWindowClosingHandler(new Window.ClosingHandler() {

            @Override
            public void onWindowClosing(ClosingEvent event) {
                event.setMessage(messageString);
            }
        });

        // on page close, back etc. send a screen event to the server
        Window.addCloseHandler(new CloseHandler<Window>() {

            @Override
            public void onClose(CloseEvent<Window> event) {
                submissionService.submitScreenChange(userResults.getUserData().getUserId(), "BrowserWindowClosed");
                presenter.fireWindowClosing();
            }
        });
    }
//    @Override
//    public void requestApplicationState(ApplicationState applicationState) {
//        try {
//            trackView(applicationState.name());
//            History.newItem(applicationState.name(), false);
    // todo:
    // on each state change check if there is an completed game data, if the share is true then upload or store if offline
    // when any stored data is uploaded then delete the store 
    // on new game play erase any in memory game data regardless of its shared or not shared state
//            switch (applicationState) {
//                case menu:
//                    userResults.setPendingStimuliGroup(null);
//                    this.presenter = new MenuPresenter(widgetTag);
//                    presenter.setState(this, null, null);
//                    break;
//                case locale:
//                    this.presenter = new LocalePresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.startscreen, null);
//                    break;
//                case version:
//                    this.presenter = new VersionPresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.startscreen, null);
//                    break;
//                case stopSharing:
//                    this.presenter = new StopSharingPresenter(widgetTag, userResults);
//                    presenter.setState(this, ApplicationState.playerdetails, null);
//                    break;
//                case tutorial:
//                    this.presenter = new TutorialPresenter(widgetTag, userResults, new AudioPlayer(this), this);
//                    presenter.setState(this, ApplicationState.version, ApplicationState.startscreen);
//                    break;
//                case chooseplayer:
//                    // only if there is an existing user, show the choose player screen
//                    if (localStorage.getLastUserId() != null) {
//                        this.presenter = new ChoosePlayerPresenter(widgetTag, localStorage, userResults, new AudioPlayer(this), this);
//                        presenter.setState(this, ApplicationState.version, ApplicationState.playerdetails);
//                        break;
//                    }
//                case tutorialorguessround:
//                    // only if the user has not played before, show the tutorial
//                    if (userResults.getUserData().getGamesPlayed() < 1) {
//                        this.presenter = new TutorialPresenter(widgetTag, userResults, new AudioPlayer(this), this);
//                        presenter.setState(this, ApplicationState.version, ApplicationState.explaindatasharing);
//                        break;
//                    }
//                case explaindatasharing:
//                    boolean shareAgreed = metadataFieldProvider.shareMetadataField.getControlledVocabulary()[0].equals(userResults.getUserData().getMetadataValue(metadataFieldProvider.shareMetadataField));
//                    if (!shareAgreed) {
//                        this.presenter = new ExplainDataSharingScreenPresenter(widgetTag, userResults, new AudioPlayer(this), this);
//                        presenter.setState(this, ApplicationState.infoscreen, ApplicationState.guessround);
//                        break;
//                    }
//                case guessround:
//                    this.presenter = new GuessRoundPresenter(widgetTag, userResults, new AudioPlayer(this));
//                    presenter.setState(this, ApplicationState.tutorial, ApplicationState.scores);
//                    break;
//                case playerdetails:
//                    this.presenter = new PlayerDetailsPresenter(widgetTag, userResults, new AudioPlayer(this), this);
//                    presenter.setState(this, ApplicationState.version, ApplicationState.chooseplayer);
//                    break;
//                case start:
// todo:            // if no player data then go to game
    // if one or more player data then go to select player
//                    this.presenter = new LocalStoragePresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.infoscreen, ApplicationState.startscreen);
//                    break;
//                case startscreen:
//                    this.presenter = new StartScreenPresenter(widgetTag, userResults, new AudioPlayer(this), this);
//                    presenter.setState(this, ApplicationState.infoscreen, ApplicationState.chooseplayer); // if there are already users otherwise go the the game
//                    break;
//                case infoscreen:
//                    this.presenter = new InfoScreenPresenter(widgetTag, userResults, new AudioPlayer(this));
//                    presenter.setState(this, ApplicationState.version, ApplicationState.scores);
//                    break;
//                case scores:
//                    this.presenter = new ScorePagePresenter(widgetTag, new AudioPlayer(this), userResults);
//                    presenter.setState(this, ApplicationState.setuser, ApplicationState.startscreen);
//                    break;
//                case matchlanguage:
//                    this.presenter = new MatchLanguagePresenter(widgetTag, new AudioPlayer(this));
//                    presenter.setState(this, ApplicationState.version, ApplicationState.map);
//                    break;
//                case map:
//                    this.presenter = new MapPresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.version, ApplicationState.moreinfo);
//                    break;
//                case moreinfo:
//                    this.presenter = new InstructionsPresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.map, ApplicationState.autotyp_regions);
//                    break;
//                case autotyp_regions:
//                    this.presenter = new AutotypRegionsMapScreen(widgetTag);
//                    presenter.setState(this, ApplicationState.moreinfo, ApplicationState.alien);
//                    break;
//                case alien:
//                    this.presenter = new AlienScreen(widgetTag);
//                    presenter.setState(this, ApplicationState.version, ApplicationState.guessround);
//                    break;
//                case start:
//                    this.presenter = new TestSvgDuplicateStringsPresenter(widgetTag);
//                    presenter.setState(this, null, ApplicationState.startscreen);
//                    break;
//                case intro:
//                    this.presenter = new IntroPresenter(widgetTag);
//                    presenter.setState(this, null, ApplicationState.guessround);
//                    break;
//                case setuser:
//                    this.presenter = new UserNamePresenter(widgetTag, userResults);
//                    presenter.setState(this, null, ApplicationState.guessround);
////                    ((MetadataPresenter) presenter).focusFirstTextBox();
//                    break;
//                case stimulus:
//                    if (userResults.getPendingStimuliGroup() == null) {
//                        this.presenter = new StimulusMenuPresenter(widgetTag, stimuliProvider, userResults);
//                        presenter.setState(this, ApplicationState.start, ApplicationState.report);
//                    } else {
//                        trackEvent(applicationState.name(), "show", userResults.getPendingStimuliGroup().getGroupLabel());
//                        this.presenter = new ColourPickerPresenter(widgetTag, userResults, 3);
//                        presenter.setState(this, null, ApplicationState.stimulus);
//                    }
//                    break;
//                case adddummyresults:
//                    final StimuliGroup[] stimuli = stimuliProvider.getStimuli();
//                    userResults.addDummyResults(stimuli[0]);
//                    userResults.addDummyResults(stimuli[1]);
//                    userResults.addDummyResults(stimuli[2]);
//                case report:
//                    this.presenter = new ReportPresenter(widgetTag, userResults);
//                    presenter.setState(this, null, ApplicationState.feedback);
//                    break;
//                case feedback:
//                    this.presenter = new FeedbackPresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.report, ApplicationState.metadata);
//                    break;
//                case metadata:
//                    this.presenter = new MetadataPresenter(widgetTag, userResults);
//                    presenter.setState(this, null, ApplicationState.registration);
//                    ((MetadataPresenter) presenter).focusFirstTextBox();
//                    break;
//                case registration:
//                    if (userResults.getStimuliGroups().isEmpty()) {
//                        this.presenter = new RegisterDisabledPresenter(widgetTag);
//                        presenter.setState(this, null, ApplicationState.stimulus);
//                    } else {
//                        this.presenter = new RegisterPresenter(widgetTag, userResults);
//                        presenter.setState(this, null, ApplicationState.moreinfo);
//                    }
//                    break;
//                case moreinfo:
//                    this.presenter = new MoreInfoPresenter(widgetTag);
//                    presenter.setState(this, ApplicationState.start, null);
//                    break;
//                case end:
//                    exitApplication();
//                    break;
//                case highscoresubmitted:
//                case highscoresfailedbuildererror:
//                case highscoresfailedconnectionerror:
//                case highscoresfailednon202:
//                case registration:
//                    break;
//                default:
//                    this.presenter = new ErrorPresenter(widgetTag, "No state for: " + applicationState);
//                    presenter.setState(this, ApplicationState.start, applicationState);
//                    break;
//            }
//        } catch (AudioException error) {
//            logger.warning(error.getMessage());
//            this.presenter = new ErrorPresenter(widgetTag, error.getMessage());
//            presenter.setState(this, ApplicationState.start, applicationState);
//        }
//    }

    @Override
    public void audioExceptionFired(AudioException audioException) {
        logger.warning(audioException.getMessage());
        this.presenter = new ErrorPresenter(widgetTag, audioException.getMessage());
        presenter.setState(this, ApplicationState.start, null);
    }

    public void start() {
        setBackButtonAction();
        submissionService.submitScreenChange(userResults.getUserData().getUserId(), "ApplicationStarted");
        // application specific information
        submissionService.submitTagValue(userResults.getUserData().getUserId(), "projectVersion", version.projectVersion(), 0);
        submissionService.submitTagValue(userResults.getUserData().getUserId(), "lastCommitDate", version.lastCommitDate().replace("\"", ""), 0);
        submissionService.submitTagValue(userResults.getUserData().getUserId(), "compileDate", version.compileDate(), 0);
        if (hasCordova()) {
            // cordova specific information
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "cordovaVersion", getCordovaVersion(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceModel", getDeviceModel(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "devicePlatform", getDevicePlatform(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceUUID", getDeviceUUID(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceVersion", getDeviceVersion(), 0);
        }
        try {
            final String appState = localStorage.getAppState();
            final ApplicationState lastAppState = (appState != null) ? ApplicationState.valueOf(appState) : ApplicationState.start;
            requestApplicationState(lastAppState);
        } catch (IllegalArgumentException argumentException) {
            requestApplicationState(ApplicationState.start);
        }
        addKeyboardEvents();
    }

    public void backAction() {
        presenter.fireBackEvent();
    }

    public void resizeAction() {
        presenter.fireResizeEvent();
    }

    private native void setBackButtonAction() /*-{
     var appController = this;
     $doc.addEventListener("backbutton", function(e){
     e.preventDefault();
     appController.@nl.mpi.tg.eg.experiment.client.AppController::backAction()();
     }, false);
     }-*/;

    private native void addKeyboardEvents() /*-{
     var appController = this;
     if($wnd.Keyboard) {
     $wnd.Keyboard.onshow = function () {
     $doc.getElementById("platformTag").innerHTML = "Keyboard.onshow GWT called";
     appController.@nl.mpi.tg.eg.experiment.client.AppController::resizeAction()();
     }
     $wnd.Keyboard.onhide = function () {
     $doc.getElementById("platformTag").innerHTML = "Keyboard.onhide GWT called";
     appController.@nl.mpi.tg.eg.experiment.client.AppController::resizeAction()();
     }
     }
     }-*/;

    protected native void exitApplication() /*-{
     $doc.navigator.app.exitApp();
     }-*/;

    protected native boolean hasCordova() /*-{
     if ($wnd.device) return true; else return false;
     }-*/;

    protected native String getCordovaVersion() /*-{
     return $wnd.device.cordova;
     }-*/;

    protected native String getDevicePlatform() /*-{
     return $wnd.device.platform;
     }-*/;

    protected native String getDeviceUUID() /*-{
     return $wnd.device.uuid;
     }-*/;

    protected native String getDeviceVersion() /*-{
     return $wnd.device.version;
     }-*/;

    protected native String getDeviceModel() /*-{
     return $wnd.device.model;
     }-*/;
}
