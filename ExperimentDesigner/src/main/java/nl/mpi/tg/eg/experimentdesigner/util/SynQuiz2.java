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
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.link;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxHeight;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxWidth;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.percentOfPage;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.src;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.target;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Jan 18, 2016 11:20:47 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SynQuiz2 {

    private final String imageSize = "80";

    public void create(PresenterScreenRepository presenterScreenRepository, PresenterFeatureRepository presenterFeatureRepository, final List<PresenterScreen> presenterScreenList) {
        presenterScreenList.add(createIntroductionScreen("Introduction", 1));
        presenterScreenList.add(createRegistrationScreen("Registration", 2));
        presenterScreenList.add(createDemographicsScreen("Demographics", 3));
        presenterScreenList.add(createStimulusScreen("Weekdays", 4));
        presenterScreenList.add(createStimulusScreen("Numbers", 5));
        presenterScreenList.add(createStimulusScreen("Letters", 6));
        presenterScreenList.add(createStimulusScreen("Months", 7));
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
        targetButtonFeature.addFeatureAttributes(target, "RegistrationScreen");
        presenterFeatureList.add(targetButtonFeature);
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "For more information about synaesthesia, please see our 'About synaesthesia' page. "
                + "If you are not sure if you have synaesthesia, and want to find out, try our SynQuiz app or take a quick test at synesthete.org."));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, "This project is organised and funded by the Language & Genetics Department at the Max Planck Institute for Psycholinguistics in Nijmegen in the Netherlands, directed by Prof. Dr. Simon E. Fisher. "
                + "The synaesthesia studies are coordinated by Dr. Amanda Tilot and Dr. Sarah Graham. "
                + "If you have any questions about our research, please contact us at " + formatLink("synaesthesia@mpi.nl") + "."));
        return presenterScreen;
    }

    private String formatLink(String linkUrl) {
        return formatLink(linkUrl, linkUrl);
    }

    private String formatLink(String linkText, String linkUrl) {
        return "<a href=\"#\" onclick=\"window.open(''" + linkUrl + "'',''_system''); return false;\">" + linkText + "</a>";
    }

    private PresenterScreen createRegistrationScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.metadata, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, "Please read the " + formatLink("Participant Information Sheet", "static/synaesthesia_info_sheet_ENGLISH_webversion.pdf") + " carefully!"));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.targetButton, "Participant Information Sheet");
//        targetButtonFeature.addFeatureAttributes(target, "InformationScreen");
//        presenterFeatureList.add(targetButtonFeature);
        insertMetadataInput("First Name", ".'{'3,'}'");
        insertMetadataInput("Last Name", ".'{'3,'}'");
        insertMetadataInput("Email address", "^[^@]+@[^@]+$");
        insertMetadataInput("\"I would like to be contacted about participating in other synaesthesia research studies\" (optional)", "true|false");
        insertMetadataInput("\"By checking this box I confirm that I have read and understood the Volunteer's Information Sheet and I agree to take part in this study\"", "true|false");
        final PresenterFeature submitButtonFeature = new PresenterFeature(FeatureType.targetButton, "Submit");
        submitButtonFeature.addFeatureAttributes(target, "DemographicsScreen");
        presenterFeatureList.add(submitButtonFeature);
        return presenterScreen;
    }

    private PresenterScreen createDemographicsScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.text, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
//        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, "Please read the Participant Information Sheet carefully!"));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        presenterFeatureList.add(new PresenterFeature(FeatureType.text, ""));
//        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.targetButton, null);
//        targetButtonFeature.addFeatureAttributes(target, "informationScreen");
//        presenterFeatureList.add(targetButtonFeature);
        return presenterScreen;
    }

    private PresenterScreen createStimulusScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(screenName, screenName, null, screenName + "Screen", null, PresenterType.colourPicker, displayOrder);
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
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        return presenterScreen;
    }

    private void insertStimulusGroup(final ArrayList<Stimulus> stimuliList, String groupName, String groupItems) {
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{groupName}));
        final String[] itemArray = groupItems.split(",");
        for (String itemString : itemArray) {
            final Stimulus stimulus = new Stimulus(null, null, null, null, itemString, itemString, 0, tagSet);
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

    private void insertMetadataInput(String label, String regex) {

    }
}
