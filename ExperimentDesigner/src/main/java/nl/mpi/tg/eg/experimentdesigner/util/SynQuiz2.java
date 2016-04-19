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
package nl.mpi.tg.eg.experimentdesigner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.link;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxHeight;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxWidth;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.percentOfPage;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.src;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.target;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;

/**
 * @since Jan 18, 2016 11:20:47 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SynQuiz2 {

    private final WizardController wizardController = new WizardController();
    private final String imageSize = "80";

    public Experiment getExperiment() {
        Experiment experiment = wizardController.getExperiment("SynQuiz2", "SynQuiz2", true);
        experiment.setStimuli(new SynQuiz2().createStimuli());
        new SynQuiz2().create(experiment, experiment.getPresenterScreen());
        return experiment;
    }

    public void create(Experiment experiment, final List<PresenterScreen> presenterScreenList) {
        final PresenterScreen introductionScreen = createIntroductionScreen("Introduction", 1);
        presenterScreenList.add(introductionScreen);
        final PresenterScreen addUserSelectMenu = wizardController.addUserSelectMenu(experiment, introductionScreen, null, 2, false);
//        final PresenterScreen registrationScreen = createRegistrationScreen("Registration", 2);
//        presenterScreenList.add(registrationScreen);
        WizardData wizardData = new WizardData();
        wizardData.setMetadataScreenText("Please read the " + formatLink("Participant Information Sheet", "static/synaesthesia_info_sheet_ENGLISH_webversion.pdf") + " carefully!");
        wizardData.setFirstNameField(true);
        wizardData.setLastNameField(true);
        wizardData.setEmailAddressField(true);
        wizardData.setOptionCheckBox1("I would like to be contacted about participating in other synaesthesia research studies (optional)");
        wizardData.setMandatoryCheckBox("By checking this box I confirm that I have read and understood the Volunteer's Information Sheet and I agree to take part in this study");

//        final PresenterScreen demographicsScreen1 = createDemographicsScreen1(experiment, "Demographics1", 4);
//        presenterScreenList.add(demographicsScreen1);
        final PresenterScreen editUserScreen = wizardController.addEditUserScreen(experiment, addUserSelectMenu, "Edit User", null, 3, wizardData, null, null, "Continue", null, null, null, "Could not contact the server, please check your internet connection and try again.", false);
        final PresenterScreen demographicsScreen1 = wizardController.addEditUserScreen(experiment, editUserScreen, "Demographics1", null, 4, null, null, demographicsFields1, "Continue", null, null, null, "Could not contact the server, please check your internet connection and try again.", false);
        wizardController.addMetadata(experiment);
        editUserScreen.setNextPresenter(demographicsScreen1);

//        final PresenterScreen demographicsScreen2 = createDemographicsScreen2(experiment, "Demographics2", 4);
//        presenterScreenList.add(demographicsScreen2);
//        demographicsScreen1.setNextPresenter(demographicsScreen2);
//        demographicsScreen2.setBackPresenter(demographicsScreen1);

        final PresenterScreen menuScreen = createMenuScreen("Menu", addUserSelectMenu, 5);
        presenterScreenList.add(menuScreen);
        demographicsScreen1.setNextPresenter(menuScreen);
        final PresenterScreen reportScreen = createReportScreen("Report", menuScreen, menuScreen, 10);
        presenterScreenList.add(reportScreen);
        final PresenterScreen weekdaysScreen = createStimulusScreen("Weekdays", menuScreen, reportScreen, 6);
        presenterScreenList.add(weekdaysScreen);
        final PresenterScreen numbersScreen = createStimulusScreen("Numbers", menuScreen, reportScreen, 7);
        presenterScreenList.add(numbersScreen);
        final PresenterScreen lettersScreen = createStimulusScreen("Letters", menuScreen, reportScreen, 8);
        presenterScreenList.add(lettersScreen);
        presenterScreenList.add(createStimulusScreen("Months", menuScreen, reportScreen, 9));
    }

    private PresenterScreen createIntroductionScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen("Decoding the Genetics of Synaesthesia", screenName, null, screenName + "Screen", null, PresenterType.text, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
//        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "We are studying the genetic basis of synaesthesia, a neurological phenomenon described as a \"mixing of the senses\". To find out how our genes shape how we see the world, "
                + "we are looking for people who connect letters, numbers, days of the week, or months with specific colours. This is called \"grapheme-colour\" synaesthesia. "));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "How our study works:"));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        final PresenterFeature studyDiagramFeature = new PresenterFeature(FeatureType.image, null);
        studyDiagramFeature.addFeatureAttributes(percentOfPage, imageSize);
        studyDiagramFeature.addFeatureAttributes(maxHeight, imageSize);
        studyDiagramFeature.addFeatureAttributes(maxWidth, imageSize);
        studyDiagramFeature.addFeatureAttributes(src, "static/study_diagram.svg");
        studyDiagramFeature.addFeatureAttributes(link, "");
        presenterFeatureList.add(studyDiagramFeature);
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "The synaesthesia tests take about 20 minutes to complete, and you can choose the ones that apply to you. "
                + "Depending on your scores, we may send you an email inviting you to participate in the genetics part of the study. There is no cost to participate, and you can do everything from home."));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.targetButton, "Participate!");
        targetButtonFeature.addFeatureAttributes(target, "Edit_User");
        presenterFeatureList.add(targetButtonFeature);
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, "For more information about synaesthesia, please see our 'About synaesthesia' page. "
                + "If you are not sure if you have synaesthesia, and want to find out, try our SynQuiz app or take a quick test at synesthete.org."));
        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, "This project is organised and funded by the Language & Genetics Department at the Max Planck Institute for Psycholinguistics in Nijmegen in the Netherlands, directed by Prof. Dr. Simon E. Fisher. "
                + "The synaesthesia studies are coordinated by Dr. Amanda Tilot and Dr. Sarah Graham. "
                + "If you have any questions about our research, please contact us at " + formatLink("mailto:synaesthesia@mpi.nl") + "."));
        return presenterScreen;
    }

    private String formatLink(String linkUrl) {
        return formatLink(linkUrl, linkUrl);
    }

    private String formatLink(String linkText, String linkUrl) {
        return "<a href=\"#\" onclick=\"window.open('" + linkUrl + "','_system'); return false;\">" + linkText + "</a>";
    }

    private PresenterScreen createRegistrationScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.metadata, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, ));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.targetButton, "Participant Information Sheet");
//        targetButtonFeature.addFeatureAttributes(target, "InformationScreen");
//        presenterFeatureList.add(targetButtonFeature);
//        insertMetadataInput("First Name", ".'{'3,'}'");
//        insertMetadataInput("Last Name", ".'{'3,'}'");
//        insertMetadataInput("Email address", "^[^@]+@[^@]+$");
//        insertMetadataInput("\"I would like to be contacted about participating in other synaesthesia research studies\" (optional)", "true|false");
//        insertMetadataInput("\"By checking this box I confirm that I have read and understood the Volunteer's Information Sheet and I agree to take part in this study\"", "true|false");
        presenterFeatureList.add(new PresenterFeature(FeatureType.allMetadataFields, null));
        final PresenterFeature submitButtonFeature = new PresenterFeature(FeatureType.targetButton, "Submit");
        submitButtonFeature.addFeatureAttributes(target, "DemographicsScreen");
        presenterFeatureList.add(submitButtonFeature);
        return presenterScreen;
    }
// todo: show complete on test that have been done like in SynQuiz1
// todo: add finish button on the test menu screen which submits all data and leads to a restart(erase) all
    
    private PresenterScreen createMenuScreen(String screenName, final PresenterScreen backScreen, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, backScreen, screenName + "Screen", null, PresenterType.menu, displayOrder);
        final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.menuItem, "WeekdaysScreen");
        presenterFeature1.addFeatureAttributes(FeatureAttribute.target, "WeekdaysScreen");
        presenterScreen.getPresenterFeatureList().add(presenterFeature1);
        final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.menuItem, "NumbersScreen");
        presenterFeature2.addFeatureAttributes(FeatureAttribute.target, "NumbersScreen");
        presenterScreen.getPresenterFeatureList().add(presenterFeature2);
        final PresenterFeature presenterFeature3 = new PresenterFeature(FeatureType.menuItem, "LettersScreen");
        presenterFeature3.addFeatureAttributes(FeatureAttribute.target, "LettersScreen");
        presenterScreen.getPresenterFeatureList().add(presenterFeature3);
        final PresenterFeature presenterFeature4 = new PresenterFeature(FeatureType.menuItem, "MonthsScreen");
        presenterFeature4.addFeatureAttributes(FeatureAttribute.target, "MonthsScreen");
        presenterScreen.getPresenterFeatureList().add(presenterFeature4);
        return presenterScreen;
    }

    final String[] demographicsFields1 = new String[]{
            "DateOfBirth:Date of Birth:0-30-9/0-10-9/1-20-90-90-9:Please enter in the standard format DD/MM/YYYY.",
        "Age:Age:0-9+:Please enter in number format.",
        "male:Male:true|false:Please enter true or false.",
        "female:Female:true|false:Please enter true or false.",
        "AbsolutePitch:Absolute pitch:true|false:Please enter true or false.",
        "TraumaticBlowToTheHead:Traumatic blow to the head:true|false:Please enter true or false.",
        "Migraines:Migraines:true|false:Please enter true or false.",
        "Headaches:Headaches:true|false:Please enter true or false.",
        "Seizures:Seizures:true|false:Please enter true or false.",
        "Dyslexia:Dyslexia:true|false:Please enter true or false.",
        "BrainSurgery:Brain surgery:true|false:Please enter true or false.",
        "AnyOtherConditions:Are there any other conditions that you would like us to know about?:.:."
    };
//    private PresenterScreen createDemographicsScreen1(Experiment experiment, String screenName, long displayOrder) {
//        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.metadata, displayOrder);
//        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
//        
//        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.saveMetadataButton, "Take the tests!");
//        presenterFeatureList.add(targetButtonFeature);
//        return presenterScreen;
//    }

    private PresenterScreen createDemographicsScreen2(Experiment experiment, String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.metadata, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
//        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "Tell Us About Your Synaesthesia"));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "Our study at the Max Planck Institute focuses on synaesthesia where numbers, letters, weekdays, or months cause people to have a colour experience. To someone with synaesthesia, the letter A might \"mean\" red to them, or the number \"5\" might make them experience the colour green. Please let us know if you experience any other types of synaesthesia by checking the boxes below. We may contact you in the future about studies related to these other types."));
        wizardController.insertMetadataField(experiment, "Numbers->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign -> Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign -> Color", presenterScreen);
        wizardController.insertMetadataField(experiment, "Numbers->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign->Smell", presenterScreen);
        wizardController.insertMetadataField(experiment, "Numbers->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign->Sound", presenterScreen);
        wizardController.insertMetadataField(experiment, "Numbers->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments>Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign->Spatial", presenterScreen);
        wizardController.insertMetadataField(experiment, "Numbers->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments>Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign->Taste", presenterScreen);
        wizardController.insertMetadataField(experiment, "Numbers->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Letters->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Weekdays->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Months->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sequences->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Pitch->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Chord->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Musical Instruments->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Taste->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Smell->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Pain->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Personalities->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Touch->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Temperature->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Vision->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "Sound->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "American Sign->Touch", presenterScreen);
        wizardController.insertMetadataField(experiment, "British Sign->Touch", presenterScreen);

        wizardController.insertMetadataField(experiment, new Metadata("AnyOtherTypes", "If you experience any other types, please explain below.", "", "", false, null), presenterScreen);

//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.saveMetadataButton, "Take the tests!");
        presenterFeatureList.add(targetButtonFeature);
        return presenterScreen;
    }

    private PresenterScreen createStimulusScreen(String screenName, final PresenterScreen backPresenter, final PresenterScreen nextPresenter, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, backPresenter, screenName + "Screen", nextPresenter, PresenterType.colourPicker, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadAllStimulus, null);
        loadStimuliFeature.addStimulusTag(screenName);
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, screenName);
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "true");
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.text, "end of stimuli"));
//        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
//        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
//        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.autoNextPresenter, null));
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        return presenterScreen;
    }

    private PresenterScreen createReportScreen(String screenName, final PresenterScreen backPresenter, final PresenterScreen nextPresenter, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, backPresenter, screenName + "Screen", nextPresenter, PresenterType.colourReport, displayOrder);
//        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        final PresenterFeature showColourReport = new PresenterFeature(FeatureType.showColourReport, null);
//        showColourReport.addStimulusTag(screenName);
        showColourReport.addFeatureAttributes(FeatureAttribute.scoreThreshold, "1");
//        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "true");
//        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.aboveThreshold, null);
        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "above threshold"));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        showColourReport.getPresenterFeatureList().add(aboveThreshold);
        final PresenterFeature belowThreshold = new PresenterFeature(FeatureType.belowThreshold, null);
        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "below threshold"));
//        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
//        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
//        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.autoNextPresenter, null));
        showColourReport.getPresenterFeatureList().add(belowThreshold);
        presenterScreen.getPresenterFeatureList().add(showColourReport);
        return presenterScreen;
    }

    private void insertStimulusGroup(final ArrayList<Stimulus> stimuliList, String groupName, String groupItems) {
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{groupName}));
        final String[] itemArray = groupItems.split(",");
        for (String itemString : itemArray) {
            final Stimulus stimulus = new Stimulus(null, null, null, null, null, itemString, itemString, 0, tagSet);
            stimuliList.add(stimulus);
        }
    }

    public ArrayList<Stimulus> createStimuli() {
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();
        insertStimulusGroup(stimuliList, "Weekdays", "Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday");
        insertStimulusGroup(stimuliList, "Numbers", "0,1,2,3,4,5,6,7,8,9");
        insertStimulusGroup(stimuliList, "Letters", "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z");
        insertStimulusGroup(stimuliList, "Months", "January,February,March,April,May,June,July,August,September,October,November,December");
        return stimuliList;
    }
}
