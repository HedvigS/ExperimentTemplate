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

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.dao.MetadataRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.StimuliSubAction;

/**
 * @since Feb 22, 2016 4:39:04 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class ShawiFieldKit {

    private final WizardController wizardController = new WizardController();

    public Experiment getShawiExperiment(MetadataRepository metadataRepository, PresenterFeatureRepository presenterFeatureRepository, PresenterScreenRepository presenterScreenRepository) {
        Experiment experiment = wizardController.getExperiment(metadataRepository, presenterFeatureRepository, presenterScreenRepository, "shawifieldkit", "Shawi FieldKit");
        experiment.setBackgroundColour("#ffeda0");
        experiment.setPrimaryColour4("#feb24c");
        experiment.setPrimaryColour2("#f03b20");
        wizardController.addMetadata(experiment);
        final PresenterScreen autoMenuPresenter = wizardController.addAutoMenu(experiment);
        final PresenterScreen welcomePresenter = wizardController.addWelcomeScreen(experiment, autoMenuPresenter, null);
        final PresenterScreen welcomeMenuPresenter = wizardController.addWelcomeMenu(experiment, welcomePresenter, null);
        final PresenterScreen instructionsPresenter = wizardController.addInstructionsScreen(experiment, welcomePresenter, null);

        StimuliSubAction[] picturesValuesArray = new StimuliSubAction[]{new StimuliSubAction("80", "the informant talks/says whatever s/he wants", "next")};
        StimuliSubAction[] grammaticalityValuesArray = new StimuliSubAction[]{new StimuliSubAction("80", new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})};
// this should not be random but use alphanum sorting 
        final PresenterScreen grammaticalityScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, welcomePresenter, new String[]{"Grammaticality"}, grammaticalityValuesArray);
        final PresenterScreen picturesScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, grammaticalityScreen, new String[]{"Pictures"}, picturesValuesArray);
        final PresenterScreen animalsScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, grammaticalityScreen, new String[]{"Animals"}, picturesValuesArray);
        final PresenterScreen frogsScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, grammaticalityScreen, new String[]{"Frogs"}, picturesValuesArray);
        final PresenterScreen metadataScreen = wizardController.createMetadataScreen(experiment, autoMenuPresenter, picturesScreen, new String[]{"Nombre", "Sexo", "Edad", "Estado civil", "Origen", "Lugar de residencia", "Nombre de la comunidad a la que pertenece", "Actividad laboral", "Nivel de estudios", "Número de hijos", "Religión", "Idiomas"});
        final PresenterScreen selectUserPresenter = wizardController.addUserSelectMenu(experiment, welcomePresenter, metadataScreen);
        final PresenterScreen editUserPresenter = wizardController.addEditUserScreen(experiment, welcomePresenter, metadataScreen);
        final PresenterScreen debugScreenPresenter = wizardController.addDebugScreen(experiment, autoMenuPresenter);
        final PresenterScreen kinshipPresenter = addKinshipScreen(experiment, autoMenuPresenter, null);
        return experiment;
    }

    public PresenterScreen addKinshipScreen(final Experiment experiment, final PresenterScreen backPresenter, final PresenterScreen nextPresenter) {
        final PresenterScreen presenterScreen = new PresenterScreen("Kinship", "Kinship", backPresenter, "Kinship", nextPresenter, PresenterType.kindiagram);
        final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.addKinTypeGui, null);
        presenterFeature1.addFeatureAttributes(FeatureAttribute.diagramName, "kinDiagram");
        presenterScreen.getPresenterFeatureList().add(presenterFeature1);
        final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.loadKinTypeStringDiagram, null);
        presenterFeature2.addFeatureAttributes(FeatureAttribute.diagramName, "kinDiagram");
        presenterFeature2.addFeatureAttributes(FeatureAttribute.timeToNext, "0");
        presenterScreen.getPresenterFeatureList().add(presenterFeature2);
        experiment.getPresenterScreen().add(presenterScreen);
        return presenterScreen;
    }
}