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

import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since May 6, 2016 4:19:54 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardTextScreen extends AbstractWizardScreen {

    public WizardTextScreen(final String screenName, String screenText, final String nextButtonLabel) {
        this.nextButton = nextButtonLabel;
        this.screenText = screenText;
        this.screenTitle = screenName;

    }

    @Override
    public PresenterScreen populatePresenterScreen(Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        presenterScreen.setPresenterType(PresenterType.text);
        super.populatePresenterScreen(experiment, obfuscateScreenNames, displayOrder);
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, screenText));
        final PresenterFeature actionButtonFeature = new PresenterFeature(FeatureType.actionButton, nextButton);
        actionButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, "SPACE");
        presenterScreen.getPresenterFeatureList().add(actionButtonFeature);
        actionButtonFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.autoNextPresenter, null));
        experiment.getPresenterScreen().add(presenterScreen);
        return presenterScreen;
    }
}
