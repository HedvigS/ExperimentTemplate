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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since May 4, 2016 2:01:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class ShawiFieldKitTest {

    /**
     * Test of getShawiExperiment method, of class ShawiFieldKit.* @throws
     * java.io.IOException
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testGetShawiExperiment() throws IOException, JAXBException, URISyntaxException {
        System.out.println("getShawiExperiment");
        ShawiFieldKit instance = new ShawiFieldKit();
        URI testXmlUri = this.getClass().getResource("/frinex-rest-output/shawifieldkit.xml").toURI();
        String expResult = new String(Files.readAllBytes(Paths.get(testXmlUri)), StandardCharsets.UTF_8);
        Experiment result = instance.getShawiExperiment();
        result.getPresenterScreen().sort(new Comparator<PresenterScreen>() {
            // because the experiment has not been stored and retrieved from the DB we need to sort this manually
            @Override
            public int compare(PresenterScreen o1, PresenterScreen o2) {
                return Long.compare(o1.getDisplayOrder(), o2.getDisplayOrder());
            }
        });
        JAXBContext jaxbContext = JAXBContext.newInstance(Experiment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
//        jaxbMarshaller.marshal(result, System.out);
        jaxbMarshaller.marshal(result, new File(new File(testXmlUri).getParentFile(), "shawifieldkit-testoutput.xml"));
        jaxbMarshaller.marshal(result, stringWriter);
        assertEquals(expResult, stringWriter.toString());
    }
}
