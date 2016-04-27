/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.plaggie;

import java.io.FileNotFoundException;
import java.io.IOException;
import plag.parser.plaggie.Configuration;
import plag.parser.plaggie.ConfigurationException;

/**
 *
 * @author Luis
 */
public class PlaggieConfiguration extends Configuration{

    public PlaggieConfiguration() throws FileNotFoundException, IOException, ConfigurationException {
        
        ////////////////////////////////////////////////////////////////////////
        // Common detection parameters
        ////////////////////////////////////////////////////////////////////////
        
        //-- codeTokenizer --
        // The tokenizer to use for the program code. Has to be a class, which
        // implements interface plag.parser.CodeTokenizer
        codeTokenizer = "plag.parser.java.JavaTokenizer";
        
        //-- filenameFilter --
        // The filter to use for filtering out non-program code files. Must
        // implement the java.io.FilenameFilter interface
        filenameFilter="plag.parser.java.JavaFilenameFilter";

        //-- minimumMatchLength --
        // The minimum match length. This is the minimum length of similar
        // token sequences that are taken into account when running the
        // plagiarism detection between two files. 11 is a good value for most
        // cases. With very small or large assignments this value can be
        // modified accordingly.
        minimumMatchLength=3;

        //-- minimumSubmissionSimilarityValue --
        // Only used, when checking multiple submissions. Detection results
        // that have one of the similarity values over this threshold, are added
        // to the report. A good value for this depends on the type of
        // submissions, usually about 0.50 does fine.
        minimumSubmissionSimilarityValue=0.5;

        //-- maximumDetectionResultsToReport --
        // Limits the number of detection results appearing in the detection
        // report. If more than this amount of detection results exceed the
        // minimumSubmissionSimilarityValue or are blacklisted, then the ones
        // with the lowest product of similarities A and B are dropped from
        // the report.
        maximumDetectionResultsToReport=100;

        ////////////////////////////////////////////////////////////////////////
        // Submission directory structure
        ////////////////////////////////////////////////////////////////////////

        //-- useRecursive --
        // Whether to recurse into subdirectories in the submission
        // directories. Only used, when checking directory submissions.
        useRecursive=true;

        //-- severalSubmissionDirectories --
        // If true, submissions are stored in subdirectories named like
        // <studentid>/<submissionid>/ and there are submissions from several
        // rounds below each studentid
        severalSubmissionDirectories=false;

        //-- submissionDirectory --
        // Only used, when severalSubmissionDirectories=true. Name of the
        // <submissionid> directory
        submissionDirectory="round1";

        ////////////////////////////////////////////////////////////////////////
        // Code exclusion parameters
        ////////////////////////////////////////////////////////////////////////

        //-- excludeInterfaces --
        // Only used when checking directory submissions. If true, Java
        // interface code is excluded from the comparison algorithm.
        excludeInterfaces=true;

        //-- excludeFiles --
        // Only used when checking directory submissions. A comma-separated
        // list of file names to be excluded from the
        // submissions. E.g. classes provided within the exercise
        // definition should be listed here. 
        excludeFiles="ExistingCode1.java,ExistingCode2.java";

        //-- excludeSubdirectories --
        // Only used when recursively checking directory submissions. Source
        // files stored in the following comma-separated list of
        // subdirectories are excluded from the comparison. 
        excludeSubdirectories="webstore,webstore/server,webstore/remote,webstore/servlets";

        //-- templates --
        // The code in the following comma-separeted list of files is excluded
        // from the detection. E.g. if some template code has been given in
        // the assignment description for students to start working with, the
        // template code should be listed here. Note! Specifying files here
        // slows down the algorithm quite a bit.
        templates="";

        ////////////////////////////////////////////////////////////////////////
        // Common report generating paramters
        ////////////////////////////////////////////////////////////////////////

        //-- minimumFileSimilarityValueToReport --
        // Only used when checking directory submissions. Detection reports
        // between two files are only added to the submission detection
        // report, if the similarity values between the files exceed this
        // value.
        minimumFileSimilarityValueToReport=0.50;

        //-- htmlReport --
        // Whether to generate HTML report of submissions. If true, an HTML
        // format detection report is generated in the directory specified by
        // parameter htmlDir.
        htmlReport=true;

        //-- blacklistFile --
        // Blacklist file, plagiarism detection results of student id's listed
        // in the blacklist file are always included in the detection results
        // and are highlighted. Note, NOT IMPLEMENTED!
        blacklistFile="blacklist.text";

        //-- showAllBlacklistedResults
        // If set to true, the detection results of blacklisted students are
        // always shown in the report, even when the similarity value does not
        // exceed the threshold. If false, the blacklisted student id's are
        // only highlighted.
        showAllBlacklistedResults=false;

        ////////////////////////////////////////////////////////////////////////
        // HTML report parameters
        // These are only used when htmlReport = true.
        ////////////////////////////////////////////////////////////////////////

        //-- htmlDir --
        // Only used, when making an HTML report (htmlReport =
        // true). Specifies the directory, in which the report is
        // generated. This directory has to exist and should be empty.
        htmlDir="html";

        ////////////////////////////////////////////////////////////////////////
        // Text report parameters
        // These are only used when htmlReport = false.
        ////////////////////////////////////////////////////////////////////////

        //-- printTokenLists --
        // Only used, when making a textual report (htmlReport = false) If
        // true, the generated token lists of each file are printed on the
        // standard output.
        printTokenLists=false;

        //-- fileDetectionReports --
        // Only used, when making a textual report (htmlReport = false). Can
        // be "full" (all matched token sections displayed) "stats" (only
        // statistics of matches between two files displayed) or "none" (no
        // report)
        //plag.parser.plaggie.fileDetectionReports="stats";
        statFileDetectionReports=true;
        
        ////////////////////////////////////////////////////////////////////////
        // Internal operational parameters
        // Usually there should be no reason to change these
        ////////////////////////////////////////////////////////////////////////

        //-- debugMessages --
        // Turn on debug messages. Only for development purposes.
        debugMessages=false;

        //-- cacheTokenLists --
        // Whether to cache the generated token lists
        // instead of generating the again each time a file is compared to
        // another. Should always be true, if false, the program uses less
        // memory but is much slower.
        cacheTokenLists=true;

        //-- createResultFile --
        // If true, the detection results are stored in a (binary) file.
        // This is mainly for debugging and testing of the report generation,
        // because it is faster to read existing reports from file than to
        // generate them again.
        createResultFile=false;

        //-- readResultsFromFile --
        // If true, the results are read from a (binary) file instead of
        // generating them from start.
        readResultsFromFile=false;

        //-- resultFile --
        // The name of the used results (binary) file.
        resultFile="results.data";

    }

}
