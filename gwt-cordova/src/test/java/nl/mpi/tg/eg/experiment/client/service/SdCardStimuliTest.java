/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.service;

import java.util.ArrayList;
import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus;
import nl.mpi.tg.eg.experiment.client.model.Stimulus;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @since Jan 11, 2016 2:11:44 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SdCardStimuliTest {

    /**
     * Test of insertStimulus method, of class SdCardStimuli.
     */
    @Test
    public void testInsertStimulus() {
        System.out.println("insertStimulus");
        String[] testData = new String[]{//"file:///storage/emulated/0/MPI_STIMULI/bodies/",
            //            "file:///storage/emulated/0/MPI_STIMULI/bowped/",
            //            "file:///storage/emulated/0/MPI_STIMULI/cutbreak/",
            //            "file:///storage/emulated/0/MPI_STIMULI/grammar/",
            //            "file:///storage/emulated/0/MPI_STIMULI/reciprocal/",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/01.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/01.jpg",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/02.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/03.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/04.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/05.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/06.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/07.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/08.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/09.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/10.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/11.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/12.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/13.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/14.png",
            "file:///storage/emulated/0/MPI_STIMULI/bodies/15.png",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/01-boykicksballtogirl.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/02-boykicksballtogirls.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/03-girlkicksballtoboy.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/04-boykicksballstogirl.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/05-ballrolls.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/06-ballrollstodog.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/07-dogrunsaway.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/08-girlchasesball.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/09-girlfalls.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/10-ballrollstotree.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/11-treefallsondog.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/12-girlboylifttree.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/13-girlpushesboy.mp4",
            "file:///storage/emulated/0/MPI_STIMULI/grammar/14-boysitsontree.mp4"};
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();

        SdCardStimuli instance = new SdCardStimuli(stimuliList, null, null);
        for (String stimulusPath : testData) {
            instance.insertStimulus(stimulusPath, stimulusPath.substring(stimulusPath.lastIndexOf("/") + 1));
        }
        for (Stimulus stimulus : stimuliList) {
            System.out.println("image " + stimulus.getImage());
            System.out.println("label " + stimulus.getLabel());
            System.out.println("mp3 " + stimulus.getMp3());
            System.out.println("mp4 " + stimulus.getMp4());
            System.out.println("ogg " + stimulus.getOgg());
//            System.out.println(stimulus.getTags());
            System.out.println("id " + stimulus.getUniqueId());
            System.out.println("is image " + stimulus.isImage());
            System.out.println("is mp3 " + stimulus.isMp3());
            System.out.println("is mp4 " + stimulus.isMp4());
            System.out.println("is ogg " + stimulus.isOgg());
            assertEquals(stimulus.isOgg(), stimulus.isOgg() ? stimulus.getOgg().endsWith(".ogg") : false);
            assertEquals(stimulus.isMp3(), stimulus.isMp3() ? stimulus.getMp3().endsWith(".mp3") : false);
            assertEquals(stimulus.isMp4(), stimulus.isMp4() ? stimulus.getMp4().endsWith(".mp4") : false);
            assertEquals(stimulus.isImage(), stimulus.isImage() ? stimulus.getImage().endsWith(".png") || stimulus.getImage().endsWith(".jpg") : false);
        }
    }
}