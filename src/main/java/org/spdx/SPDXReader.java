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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SPDXDocument;
import org.spdx.rdfparser.SPDXDocumentFactory;
import org.spdx.spdxspreadsheet.SPDXSpreadsheet;
import org.spdx.spdxspreadsheet.SpreadsheetException;
import org.spdx.spdxspreadsheet.SpreadsheetToRDF;
import org.spdx.tag.BuildDocument;
import org.spdx.tag.CommonCode;
import org.spdx.tag.TagValueLexer;
import org.spdx.tag.TagValueParser;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * One stop shop for all format conversion functionality.
 *
 * @author Edo.Shor
 */
public class SPDXReader {

    /* --- Public methods --- */

    /**
     * Read SPDX Document from a RDFa file.
     *
     * @param rdfFile RDFa file location.
     *
     * @return Analysis of the given file.
     *
     * @throws IOException In case of errors reading the given file.
     * @throws InvalidSPDXAnalysisException In case of errors in the file format.
     */
    public SPDXDocument readRdf(File rdfFile) throws IOException, InvalidSPDXAnalysisException {
        return SPDXDocumentFactory.creatSpdxDocument(rdfFile.getAbsolutePath());
    }

    /**
     * Read SPDX Document from an Excel spreadsheet file.
     *
     * @param ssFile Excel file location.
     *
     * @return Analysis of the given file.
     *
     * @throws IOException In case of errors reading the given file.
     * @throws InvalidSPDXAnalysisException In case of errors in the file format.
     * @throws SpreadsheetException In case of error in the spreadsheet format.
     */
    public SPDXDocument readSpreadsheet(File ssFile) throws IOException, InvalidSPDXAnalysisException, SpreadsheetException {
        Model model = ModelFactory.createDefaultModel();
        SPDXDocument analysis = new SPDXDocument(model);
        SPDXSpreadsheet ss = new SPDXSpreadsheet(ssFile, false, true);
        SpreadsheetToRDF.copySpreadsheetToSPDXAnalysis(ss, analysis);
        return analysis;
    }

    /**
     * Read SPDX Document from a tag value file.
     *
     * @param tagFile Tag value file location.
     *
     * @return Analysis of the given file.
     *
     * @throws IOException In case of errors reading the given file.
     * @throws InvalidSPDXAnalysisException In case of errors in the file SPDX format.
     * @throws RecognitionException In case of errors in the tag value format.
     * @throws TokenStreamException
     * @throws Exception In case of an unchecked exception while parsing the tag value file.
     */
    public SPDXDocument readTagFile(File tagFile) throws Exception{
        SPDXDocument analysis;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tagFile);
            TagValueLexer lexer = new TagValueLexer(new DataInputStream(fis));
            TagValueParser parser = new TagValueParser(lexer);
            Model model = ModelFactory.createDefaultModel();
            analysis = new SPDXDocument(model);
            Properties constants = CommonCode.getTextFromProperties("SpdxTagValueConstants.properties");
            parser.setBehavior(new BuildDocument(model, analysis, constants));
            parser.data();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return analysis;
    }

}
