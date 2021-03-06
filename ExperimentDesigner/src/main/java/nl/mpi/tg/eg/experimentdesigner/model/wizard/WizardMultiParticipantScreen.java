/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Nov 7, 2016 1:59:52 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardMultiParticipantScreen extends AbstractWizardScreen {

    public WizardMultiParticipantScreen() {
        super(WizardScreenEnum.WizardMultiParticipantScreen, "MultiParticipant", "MultiParticipant", "MultiParticipant");
    }

    public WizardMultiParticipantScreen(final String screenName,
            final String groupMembers,
            final String communicationChannels,
            final String textEntryPhaseRoles,
            final String textEntryPhaseText,
            final String textWaitPhaseRoles,
            final boolean textWaitPhaseStimuluIncrement,
            final String textWaitPhaseText,
            final String gridWaitPhaseRoles,
            final String gridWaitPhaseText,
            final String responseGridPhaseRoles,
            final String responseGridPhaseText,
            final String mutualFeedbackPhaseRoles,
            final String mutualFeedbackPhaseText,
            final String trainingDisplayPhaseRoles,
            final String trainingDisplayPhaseText,
            final String preStimuliText,
            final String postStimuliText,
            final int stimuliCount,
            final int msToShow,
            final int timerCountDownProducerMs,
            final int timerCountDownGuesserMs,
            final String timerCountDownLabel
    ) {
        super(WizardScreenEnum.WizardMultiParticipantScreen, screenName, screenName, screenName);
        setRandomiseStimuli(true);
        this.wizardScreenData.setStimuliCount(stimuliCount);
        setStimulusMsDelay(msToShow);
        setStimulusFreeText(false);
        setAllowHotkeyButtons(false);
//        this.wizardScreenData.setButtonLabelEventTag("");
        this.wizardScreenData.setCentreScreen(true);
        this.wizardScreenData.setGroupMembers(groupMembers);
        this.wizardScreenData.setGroupCommunicationChannels(communicationChannels);
        this.wizardScreenData.setGroupPhasesRoles(new String[]{textEntryPhaseRoles, textWaitPhaseRoles, gridWaitPhaseRoles, responseGridPhaseRoles, mutualFeedbackPhaseRoles, trainingDisplayPhaseRoles});

        this.wizardScreenData.setTaskIndex((textWaitPhaseStimuluIncrement) ? 1 : 0);
        this.wizardScreenData.setScreenText(0, textEntryPhaseText);
        this.wizardScreenData.setScreenText(1, gridWaitPhaseText);
        this.wizardScreenData.setScreenText(2, textWaitPhaseText);
        this.wizardScreenData.setScreenText(3, responseGridPhaseText);
        this.wizardScreenData.setScreenText(4, mutualFeedbackPhaseText);
        this.wizardScreenData.setScreenText(5, trainingDisplayPhaseText);
        this.wizardScreenData.setScreenText(6, "");
        this.wizardScreenData.setScreenText(7, "");
        this.wizardScreenData.setScreenText(8, preStimuliText);
        this.wizardScreenData.setScreenText(9, postStimuliText);
        setTimerCountDownGuesserMs(timerCountDownGuesserMs);
        setTimerCountDownProducerMs(timerCountDownProducerMs);
        setTimerCountDownLabel(timerCountDownLabel);
        setAllowedCharCodes("etuiopasdfgkzbnm ");
    }

    final public void setRandomiseStimuli(Boolean randomiseStimuli) {
        this.wizardScreenData.setScreenBoolean(0, randomiseStimuli);
    }

    final public void setStimulusFreeText(Boolean stimulusFreeText) {
        this.wizardScreenData.setScreenBoolean(1, stimulusFreeText);
    }

    final public void setAllowHotkeyButtons(Boolean allowHotkeyButtons) {
        this.wizardScreenData.setScreenBoolean(2, allowHotkeyButtons);
    }

    private boolean isRandomiseStimuli(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    private boolean isStimulusFreeText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(1);
    }

    private String getPreStimuliText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(8);
    }

    private String getPostStimuliText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(9);
    }

    private String getTimerCountDownLabel(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(10);
    }

    final public void setTimerCountDownLabel(String timerCountDownLabel) {
        this.wizardScreenData.setScreenText(10, timerCountDownLabel);
    }

    private String getAllowedCharCodes(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(7);
    }

    final public void setAllowedCharCodes(String allowedCharCodes) {
        this.wizardScreenData.setScreenText(7, allowedCharCodes);
    }

    private int getTimerCountDownProducerMs(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(2);
    }

    final public void setTimerCountDownProducerMs(int timerCountDownProducerMs) {
        this.wizardScreenData.setScreenIntegers(2, timerCountDownProducerMs);
    }

    private int getTimerCountDownGuesserMs(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(1);
    }

    final public void setTimerCountDownGuesserMs(int timerCountDownGuesserMs) {
        this.wizardScreenData.setScreenIntegers(1, timerCountDownGuesserMs);
    }

    private int getStimulusMsDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    final public void setStimulusMsDelay(int stimulusMsDelay) {
        this.wizardScreenData.setScreenIntegers(0, stimulusMsDelay);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Randomise Stimuli", "Stimulus Free Text", "Allow Hotkey Buttons", "Allowed Char Codes", "Text shown before the stimuli are presented", "Text shown after all the stimuli have been presented"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Text Entry Phase Text", "Grid Wait Phase Text", "Text Wait Phase Text", "Response Grid Phase Text", "Mutual Feedback Phase Text", "Training Display Phase Text", "Timer Count Down Ended Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"Seconds to show the screen, rather than showing a next button", "timerCountDownProducerMs", "timerCountDownGuesserMs"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public final void setStimuliSet(String[] stimuliSet) {
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        if (stimuliSet != null) {
            for (String stimulusLine : stimuliSet) {
                final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()}));
                tagSet.addAll(Arrays.asList(stimulusLine.split("(\\.png)?:")));
                final Stimulus stimulus;
                if (stimulusLine.contains(".png")) {
                    final String[] splitLine = stimulusLine.split(":");
//                    tagSet.addAll(Arrays.asList(stimulusLine.split("/")));
                    stimulus = new Stimulus(stimulusLine.replace(".png", ""), null, null, splitLine[0], null, splitLine[splitLine.length - 1], 0, tagSet, null, null);
                    stimuliList.add(stimulus);
                }
            }
        }
    }

    public void setStimulusFreeText(boolean stimulusFreeText, String freeTextValidationRegex, String freeTextValidationMessage) {
        setStimulusFreeText(stimulusFreeText);
        this.wizardScreenData.setFreeTextValidationRegex(freeTextValidationRegex);
        this.wizardScreenData.setFreeTextValidationMessage(freeTextValidationMessage);
    }

    final public void setButtonLabel(String buttonLabelEventTag) {
        this.wizardScreenData.setNextButton(new String[]{buttonLabelEventTag});
        this.wizardScreenData.setButtonLabelEventTag(buttonLabelEventTag);
    }

    private List<PresenterFeature> getScoreFeatures(boolean correctResponse) {
//        final PresenterFeature scoreButtonFeature = new PresenterFeature(FeatureType.actionButton, "correct");
//        scoreButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, "correct button");
//        scoreButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, "Z");
//        scoreButtonFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        final PresenterFeature scoreIncrement = new PresenterFeature(FeatureType.scoreIncrement, null);
        scoreIncrement.addFeatureAttributes(FeatureAttribute.scoreValue, (correctResponse) ? "true" : "false");
        final PresenterFeature scoreAboveThreshold = new PresenterFeature(FeatureType.scoreAboveThreshold, null);
        scoreAboveThreshold.addFeatureAttributes(FeatureAttribute.scoreThreshold, "5");
        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.aboveThreshold, null);
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "above threshold"));
        scoreAboveThreshold.getPresenterFeatureList().add(aboveThreshold);
        final PresenterFeature belowThreshold = new PresenterFeature(FeatureType.belowThreshold, null);
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "below threshold"));
        scoreAboveThreshold.getPresenterFeatureList().add(belowThreshold);
//        scoreButtonFeature.getPresenterFeatureList().add(scoreIncrement);
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));

//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature aboveFeature = addGroupMessageButton("correctNetworkActivityAboveThreshold", "Z");
//        final PresenterFeature belowFeature = addGroupMessageButton("correctNetworkActivityBelowThreshold", "Z");
        final PresenterFeature aboveFeature = new PresenterFeature(FeatureType.sendGroupMessage, null);
        aboveFeature.addFeatureAttributes(FeatureAttribute.eventTag, "aboveFeature");
        aboveFeature.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        final PresenterFeature belowFeature = new PresenterFeature(FeatureType.sendGroupMessage, null);
        belowFeature.addFeatureAttributes(FeatureAttribute.eventTag, "belowFeature");
        belowFeature.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");

        aboveThreshold.getPresenterFeatureList().add(aboveFeature);
        belowThreshold.getPresenterFeatureList().add(belowFeature);
        return Arrays.asList(new PresenterFeature[]{scoreIncrement, scoreAboveThreshold});
    }

    @Override
    public PresenterScreen populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = storedWizardScreenData.getPresenterScreen().getPresenterFeatureList();
        if (storedWizardScreenData.isCentreScreen()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        final PresenterFeature groupNetwork = new PresenterFeature(FeatureType.groupNetwork, null);
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupMembers, storedWizardScreenData.getGroupMembers());
        //@todo: add groupRoleSequence
//        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, 
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, storedWizardScreenData.getGroupCommunicationChannels());
//        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, 

        final PresenterFeature producerNetworkActivity0 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        selfNetworkActivity1.addFeatureAttributes(FeatureAttribute.requestedPhase, "0");
        final PresenterFeature producerNetworkActivity1 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        selfNetworkActivity2.addFeatureAttributes(FeatureAttribute.requestedPhase, "1");
        final PresenterFeature guesserNetworkActivity0 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature guesserNetworkActivity1 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature allNetworkActivity2 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        groupNetworkActivity2.addFeatureAttributes(FeatureAttribute.requestedPhase, "1");
        final PresenterFeature trainingDisplayNetworkActivity3 = new PresenterFeature(FeatureType.groupNetworkActivity, null);

        final PresenterFeature nextStimulusP = new PresenterFeature(FeatureType.nextStimulus, null);
        producerNetworkActivity0.getPresenterFeatureList().add(nextStimulusP);
        nextStimulusP.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        nextStimulusP.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusProducerNetworkActivity1" + storedWizardScreenData.getScreenTitle());

        producerNetworkActivity0.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[0]);
        guesserNetworkActivity0.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[1]);
        producerNetworkActivity1.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[2]);
        guesserNetworkActivity1.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[3]);
        allNetworkActivity2.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[4]);
        trainingDisplayNetworkActivity3.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[5]);

        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));

//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));

//        final PresenterFeature nextStimulusG = new PresenterFeature(FeatureType.nextStimulus, null);
//        guesserNetworkActivity1.getPresenterFeatureList().add(nextStimulusG);
//        nextStimulusG.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusG.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusGuesserNetworkActivity1" + storedWizardScreenData.getScreenTitle());
        if (storedWizardScreenData.getTaskIndex() == 1) {
//            final PresenterFeature nextStimulusX = new PresenterFeature(FeatureType.nextStimulus, null);
//            guesserNetworkActivity0.getPresenterFeatureList().add(nextStimulusX);
//            nextStimulusX.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//            nextStimulusX.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusFeatureTrainingDisplay" + storedWizardScreenData.getScreenTitle());
        }
        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(2)));
        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(3)));
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(4)));
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(5)));

//        if (storedWizardScreenData.getStimulusFreeText()) {
//            final PresenterFeature stimulusFreeTextFeature = new PresenterFeature(FeatureType.stimulusFreeText, storedWizardScreenData.getFreeTextValidationMessage());
//            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.validationRegex, storedWizardScreenData.getFreeTextValidationRegex());
//            groupNetworkActivity1.getPresenterFeatureList().add(stimulusFreeTextFeature);
//        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimuliFeature.addStimulusTag(storedWizardScreenData.getScreenTitle());

        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.minStimuliPerTag, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuliPerTag, "100");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(isRandomiseStimuli(storedWizardScreenData)));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(storedWizardScreenData.getStimuliCount()));
        if (getTimerCountDownProducerMs(storedWizardScreenData) > 0) {
            final PresenterFeature countDownFeature = new PresenterFeature(FeatureType.countdownLabel, getTimerCountDownLabel(storedWizardScreenData));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getTimerCountDownProducerMs(storedWizardScreenData)));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msLabelFormat, "ss"); // HH:mm:ss
            producerNetworkActivity0.getPresenterFeatureList().add(countDownFeature);
        }
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusImage, null);
        imageFeature.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        producerNetworkActivity0.getPresenterFeatureList().add(imageFeature);
        if (getTimerCountDownGuesserMs(storedWizardScreenData) > 0) {
            final PresenterFeature countDownFeature = new PresenterFeature(FeatureType.countdownLabel, getTimerCountDownLabel(storedWizardScreenData));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getTimerCountDownGuesserMs(storedWizardScreenData)));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msLabelFormat, "ss"); // HH:mm:ss
            guesserNetworkActivity1.getPresenterFeatureList().add(countDownFeature);
        }
        // temporary testing features
        final PresenterFeature stimulusGrid = addStimuliGrid("guesserNetworkActivity1Grid", getScoreFeatures(true), getScoreFeatures(false));
        guesserNetworkActivity1.getPresenterFeatureList().add(stimulusGrid);
//        final PresenterFeature resultsGrid = addStimuliGrid("resultsGrid", new PresenterFeature(FeatureType.htmlText, "response not relevant"), new PresenterFeature(FeatureType.htmlText, "response not relevant"));
//        allNetworkActivity2.getPresenterFeatureList().add(resultsGrid);

        final PresenterFeature allNetworkActivity2Image = new PresenterFeature(FeatureType.stimulusImage, null);
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        allNetworkActivity2.getPresenterFeatureList().add(allNetworkActivity2Image);

        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMessageLabel, null));

        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMessageLabel, null));

        final PresenterFeature groupResponseFeedback = new PresenterFeature(FeatureType.groupResponseFeedback, null);
        final PresenterFeature responseCorrect = new PresenterFeature(FeatureType.responseCorrect, null);
        responseCorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        groupResponseFeedback.getPresenterFeatureList().add(responseCorrect);
        final PresenterFeature responseIncorrect = new PresenterFeature(FeatureType.responseIncorrect, null);
        responseIncorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        groupResponseFeedback.getPresenterFeatureList().add(responseIncorrect);

        responseCorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "responseCorrect"));
        responseIncorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "responseIncorrect"));
        responseIncorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "your guesser chose"));

        final PresenterFeature groupResponseStimulusImage = new PresenterFeature(FeatureType.groupResponseStimulusImage, null);
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        responseIncorrect.getPresenterFeatureList().add(groupResponseStimulusImage);
        allNetworkActivity2.getPresenterFeatureList().add(groupResponseFeedback);

//        (true, false, new FeatureAttribute[]{, , }, true, false, FeatureType.Contitionals.hasCorrectIncorrect),
//        final PresenterFeature correctButton = getScoreFeatures();
//        final PresenterFeature incorrectButton = addGroupMessageButton("incorrect", "NUM_PERIOD");
//        final PresenterFeature nextStimulusFeature2 = addGroupMessageButton("groupNetworkActivity2", "Q");
//        guesserNetworkActivity1.getPresenterFeatureList().add(correctButton);
//        guesserNetworkActivity1.getPresenterFeatureList().add(incorrectButton);
        // @todo: is this inserted into the correct location
        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.submitGroupEvent, null));
        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.enableStimulusButtons, null));
//        incorrectButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.submitGroupEvent, null));

//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));

        // allNetworkActivity2 phase shows the stimulus and the selected stimulus and the message and the group score
        responseCorrect.getPresenterFeatureList().add(addGroupMessageButton("Next [enter]", "ENTER"));
        responseIncorrect.getPresenterFeatureList().add(addGroupMessageButton("Next [enter]", "ENTER"));

        final PresenterFeature stimulusImage = new PresenterFeature(FeatureType.stimulusImage, null);

        stimulusImage.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        stimulusImage.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(stimulusImage);

        final PresenterFeature groupMessageLabel = new PresenterFeature(FeatureType.groupMessageLabel, null);
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(groupMessageLabel);
        if (getStimulusMsDelay(storedWizardScreenData) > 0) {
            final PresenterFeature pause = new PresenterFeature(FeatureType.pause, null);
            final PresenterFeature sendGroupMessage = new PresenterFeature(FeatureType.sendGroupMessage, null);
            pause.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getStimulusMsDelay(storedWizardScreenData)));
            sendGroupMessage.addFeatureAttributes(FeatureAttribute.eventTag, "autoNext");
            sendGroupMessage.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
            pause.getPresenterFeatureList().add(sendGroupMessage);
            stimulusImage.getPresenterFeatureList().add(pause);
        } else {
            final PresenterFeature nextStimulusFeature4 = addGroupMessageButton("Next [enter]", "ENTER");
            stimulusImage.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
            stimulusImage.getPresenterFeatureList().add(nextStimulusFeature4);
        }
        hasMoreStimulusFeature.getPresenterFeatureList().add(groupNetwork);

        final String preStimuliText = getPreStimuliText(storedWizardScreenData);
        if (preStimuliText != null && !preStimuliText.isEmpty()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, preStimuliText));
            final PresenterFeature actionButton = new PresenterFeature(FeatureType.actionButton, "Next [enter]");
            actionButton.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            actionButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
            actionButton.getPresenterFeatureList().add(loadStimuliFeature);
            presenterFeatureList.add(actionButton);
        } else {
            presenterFeatureList.add(loadStimuliFeature);
        }
        groupNetwork.getPresenterFeatureList().add(producerNetworkActivity0);
        groupNetwork.getPresenterFeatureList().add(producerNetworkActivity1);
        groupNetwork.getPresenterFeatureList().add(guesserNetworkActivity0);
        groupNetwork.getPresenterFeatureList().add(guesserNetworkActivity1);
        groupNetwork.getPresenterFeatureList().add(allNetworkActivity2);
        groupNetwork.getPresenterFeatureList().add(trainingDisplayNetworkActivity3);
//        hasMoreStimulusFeature.getPresenterFeatureList().add(allNetworkActivity2);
//        hasMoreStimulusFeature.getPresenterFeatureList().add(guesserNetworkActivity1);

        imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        final PresenterFeature presenterFeature;
//        final String hotKeyString = "SPACE";
        presenterFeature = imageFeature;
        presenterFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (isStimulusFreeText(storedWizardScreenData)) {
            final PresenterFeature stimulusFreeTextFeature = new PresenterFeature(FeatureType.stimulusFreeText, storedWizardScreenData.getFreeTextValidationMessage());
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.validationRegex, storedWizardScreenData.getFreeTextValidationRegex());
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.allowedCharCodes, getAllowedCharCodes(storedWizardScreenData));
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            presenterFeature.getPresenterFeatureList().add(stimulusFreeTextFeature);
        }
        if (storedWizardScreenData.getStimulusResponseOptions() != null) {
            final PresenterFeature ratingFooterButtonFeature = new PresenterFeature(FeatureType.ratingButton, null);
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabels, storedWizardScreenData.getStimulusResponseOptions());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelLeft, storedWizardScreenData.getStimulusResponseLabelLeft());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelRight, storedWizardScreenData.getStimulusResponseLabelRight());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.eventTier, "1");
            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "NextStimulus" + storedWizardScreenData.getScreenTitle());
            ratingFooterButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
            presenterFeature.getPresenterFeatureList().add(ratingFooterButtonFeature);
//        } else {
//            final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, storedWizardScreenData.getNextButton()[0]);
//            nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getButtonLabelEventTag());
//            if (storedWizardScreenData.getAllowHotkeyButtons()) {
//                nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, hotKeyString);
//            }
//            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + storedWizardScreenData.getScreenTitle());
//            nextButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
//            presenterFeature.getPresenterFeatureList().add(nextButtonFeature);
        }
        // temporary testing features
        final PresenterFeature groupNetworkActivitySelf1 = addGroupMessageButton("Next [enter]", "ENTER");
        // end testing features

        presenterFeature.getPresenterFeatureList().add(groupNetworkActivitySelf1);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);

        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.autoNextPresenter, null);

        final String postStimuliText = getPostStimuliText(storedWizardScreenData);
        if (postStimuliText != null && !postStimuliText.isEmpty()) {
            endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
            endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, postStimuliText));
            final PresenterFeature actionButton = new PresenterFeature(FeatureType.actionButton, "Next [enter]");
            actionButton.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            actionButton.getPresenterFeatureList().add(autoNextPresenter);
            endOfStimulusFeature.getPresenterFeatureList().add(actionButton);
        } else {
            endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        }
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return storedWizardScreenData.getPresenterScreen();
    }

    protected PresenterFeature addStimuliGrid(String eventTagString, final List<PresenterFeature> responseCorrectFeatures, final List<PresenterFeature> responseIncorrectFeatures) {
        //        final PresenterFeature guesserRatingButtons = new PresenterFeature(FeatureType.ratingButton, null);
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabels, "one,two,three");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabelLeft, "");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabelRight, "");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.eventTier, "1");
//        groupNetworkActivity2.getPresenterFeatureList().add(guesserRatingButtons);
// temporary testing features
//        final PresenterFeature nextStimulusFeature1 = new PresenterFeature(FeatureType.sendGroupMessageButton, "groupNetworkActivity1");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.eventTag, "groupNetworkActivity1");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.hotKey, "Q");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.requestedPhase, "2");
//        guesserNetworkActivity0.getPresenterFeatureList().add(nextStimulusFeature1);
        final PresenterFeature resultsGrid = new PresenterFeature(FeatureType.showStimulusGrid, null);
        resultsGrid.addFeatureAttributes(FeatureAttribute.columnCount, "4");
        resultsGrid.addFeatureAttributes(FeatureAttribute.maxStimuli, "8");
        resultsGrid.addFeatureAttributes(FeatureAttribute.imageWidth, "100");
        resultsGrid.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        resultsGrid.addFeatureAttributes(FeatureAttribute.eventTag, eventTagString);
        final PresenterFeature responseCorrect = new PresenterFeature(FeatureType.responseCorrect, null);
        responseCorrect.getPresenterFeatureList().addAll(responseCorrectFeatures);
        responseCorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        resultsGrid.getPresenterFeatureList().add(responseCorrect);
        final PresenterFeature resultsIncorrect = new PresenterFeature(FeatureType.responseIncorrect, null);
        resultsIncorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        resultsIncorrect.getPresenterFeatureList().addAll(responseIncorrectFeatures);
        resultsGrid.getPresenterFeatureList().add(resultsIncorrect);
        return resultsGrid;
    }

//    showStimulusGrid( true, false, new FeatureAttribute[]{columnCount, imageWidth, eventTag
//    }
//     , true, false, FeatureType.Contitionals.hasCorrectIncorrect
    protected PresenterFeature addGroupMessageButton(final String labelString, final String hotKey) {
        final PresenterFeature nextStimulusFeature2 = new PresenterFeature(FeatureType.sendGroupMessageButton, labelString);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.eventTag, labelString);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.hotKey, hotKey);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        return nextStimulusFeature2;
    }
}
