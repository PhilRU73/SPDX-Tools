/**
 * Copyright (c) 2013 White Source ltd.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.spdx;

import org.junit.Ignore;
import org.junit.Test;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SPDXDocument;
import org.spdx.spdxspreadsheet.SpreadsheetException;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the
 *
 * @author Edo.Shor
 */
public class SPDXReaderTest {

    @Test
    public void testReadRdf() throws IOException, InvalidSPDXAnalysisException {
        File file = new File("Examples/SPDXRdfExample.rdf").getAbsoluteFile();
        SPDXDocument document = new SPDXReader().readRdf(file);
        assertNotNull(document);
        assertEquals(0, document.verify().size());
    }

    @Test
    public void testReadTag() throws Exception {
        File file = new File("Examples/SPDXTagExample.tag").getAbsoluteFile();
        SPDXDocument document = new SPDXReader().readTagFile(file);
        assertNotNull(document);
        assertEquals(0, document.verify().size());
    }

    @Ignore
    @Test
    public void testReadSpreadsheet() throws IOException, InvalidSPDXAnalysisException, SpreadsheetException {
        File file = new File("Examples/SPDXSpreadsheetExample.xls").getAbsoluteFile();
        SPDXDocument document = new SPDXReader().readSpreadsheet(file);
        assertNotNull(document);
        assertEquals(0, document.verify().size());
    }
}
