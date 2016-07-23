/*
 * Copyright (C) 2015 Pivotal Software, Inc.
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
package nl.mpi.tg.eg.experimentdesigner.model;

import java.util.ArrayList;
import java.util.Arrays;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureType.*;

/**
 * @since Aug 18, 2015 4:16:06 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public enum PresenterType {

    transmission(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        localStorageData,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        popupMessage,
        stimulusAudio,
        stimulusImage,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulusGrid, matchingStimulusGrid,
        showStimulus,
        pause,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        multipleUsers, singleUser,
        userInfo,
        menuItem
    }),
    metadata(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        localStorageData,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        stimulusImage,
        stimulusAudio,
        popupMessage,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        userInfo,
        menuItem,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton
    }),
    preload(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        localStorageData,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        allMenuItems,
        nextStimulusButton, ratingButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        audioButton,
        userInfo,
        menuItem,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        stimulusPause,
        stimulusLabel,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        popupMessage,
        pause,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus}),
    stimulus(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        localStorageData,
        userInfo,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        allMenuItems,
        menuItem,
        addKinTypeGui,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        preloadAllStimuli}),
    colourPicker(new FeatureType[]{
        showColourReport,
        aboveThreshold,
        belowThreshold,
        image, htmlText, plainText,
        actionFooterButton, actionButton, targetButton, targetFooterButton, addPadding, centrePage, clearPage,
        startAudioRecorder, stopAudioRecorder, startAudioRecorderTag, endAudioRecorderTag,
        loadKinTypeStringDiagram, localStorageData,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        versionData,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem}),
    colourReport(new FeatureType[]{
        helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem}),
    kindiagram(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        popupMessage,
        localStorageData,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        AnnotationTimelinePanel, VideoPanel,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        userInfo,
        stimulusImage,
        stimulusAudio
    }),
    menu(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        popupMessage,
        localStorageData,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        userInfo,
        stimulusImage,
        stimulusAudio}),
    debug(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        popupMessage,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData, eraseUsersDataButton,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        stimulusImage,
        userInfo,
        stimulusAudio}),
    text(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        userInfo,
        menuItem}),
    timeline(new FeatureType[]{
        showColourReport, helpDialogue,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadSubsetStimulus, loadStimulus, loadSdCardStimulus, loadAllStimulus,
        allMetadataFields, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulusGrid, matchingStimulusGrid,
        showStimulus,
        pause,
        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem});

    private final FeatureType[] featureTypes;

    private PresenterType(FeatureType[] excludedFeatureTypes) {
        ArrayList<FeatureType> features = new ArrayList<>();
        features.addAll(Arrays.asList(FeatureType.values()));
        for (FeatureType excludedFeature : excludedFeatureTypes) {
            features.remove(excludedFeature);
        }
        this.featureTypes = features.toArray(new FeatureType[features.size()]);
    }

    public FeatureType[] getFeatureTypes() {
        return featureTypes;
    }
}
