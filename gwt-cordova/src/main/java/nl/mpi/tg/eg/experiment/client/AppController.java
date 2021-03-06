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
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.model.UserId;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.presenter.StorageFullPresenter;
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
        boolean hasNewMetadata = false;
        for (MetadataField metadataField : metadataFieldProvider.metadataFieldArray) {
            final String postName = metadataField.getPostName();
            String value = Window.Location.getParameter(postName);
            if (value != null) {
                userResults.getUserData().setMetadataValue(metadataField, value);
                hasNewMetadata = true;
            }
        }
        if (hasNewMetadata) {
            localStorage.storeData(userResults);
        }
        String debugValue = Window.Location.getParameter("debug");
        if (debugValue != null) {
            localStorage.saveAppState(userResults.getUserData().getUserId(), ApplicationState.about.name());
        }

//        detectWindowDefocus(widgetTag);
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

//    private void detectWindowDefocus(RootLayoutPanel widgetTag) {
//        // this will most likely not work on a non input tag, however we are interested in stats on cases where it does
//        widgetTag.addHandler(new BlurHandler() {
//
//            @Override
//            public void onBlur(BlurEvent event) {
//                submissionService.submitScreenChange(userResults.getUserData().getUserId(), "widgetTag.onBlur");
//            }
//        }, BlurEvent.getType());
//        widgetTag.addHandler(new FocusHandler() {
//
//            @Override
//            public void onFocus(FocusEvent event) {
//                submissionService.submitScreenChange(userResults.getUserData().getUserId(), "widgetTag.onFocus");
//            }
//        }, FocusEvent.getType());
//    }
    @Override
    public void audioExceptionFired(AudioException audioException) {
        logger.warning(audioException.getMessage());
        this.presenter = new ErrorPresenter(widgetTag, audioException.getMessage());
        presenter.setState(this, ApplicationState.start, null);
    }

    public void start() {
        setBackButtonAction();
        try {
            submissionService.submitScreenChange(userResults.getUserData().getUserId(), "ApplicationStarted");
            // application specific information
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "projectVersion", version.projectVersion(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "lastCommitDate", version.lastCommitDate().replace("\"", ""), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "compileDate", version.compileDate(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "navigator.platform", Window.Navigator.getPlatform(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "navigator.userAgent", Window.Navigator.getUserAgent(), 0);
            submissionService.submitTagValue(userResults.getUserData().getUserId(), "navigator.cookieEnabled", Boolean.toString(Window.Navigator.isCookieEnabled()), 0);
            if (hasCordova()) {
                // cordova specific information
                submissionService.submitTagValue(userResults.getUserData().getUserId(), "cordovaVersion", getCordovaVersion(), 0);
                submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceModel", getDeviceModel(), 0);
                submissionService.submitTagValue(userResults.getUserData().getUserId(), "devicePlatform", getDevicePlatform(), 0);
                submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceUUID", getDeviceUUID(), 0);
                submissionService.submitTagValue(userResults.getUserData().getUserId(), "deviceVersion", getDeviceVersion(), 0);
            }
            try {
                final String appState = localStorage.getAppState(userResults.getUserData().getUserId());
                final ApplicationState lastAppState = (appState != null) ? ApplicationState.valueOf(appState) : ApplicationState.start;
                requestApplicationState(lastAppState);
            } catch (IllegalArgumentException argumentException) {
                requestApplicationState(ApplicationState.start);
            }
            addKeyboardEvents();
        } catch (Exception exception) {
            this.presenter = new StorageFullPresenter(widgetTag, exception.getMessage());
            presenter.setState(this, ApplicationState.start, null);
        }
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
