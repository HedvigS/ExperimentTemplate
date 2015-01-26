<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : csv2properties.xsl
    Created on : January 26, 2015, 10:28 AM
    Author     : Peter Withers <p.withers@psych.ru.nl>
    Description:
        Converts the CSV of language data into a localisable properties file.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:variable name="filename" select="(tokenize(base-uri(), '/'))[last()]"/>
    <xsl:variable name="classname" select="substring-before($filename, '.xml')"/>
    <xsl:variable name="csvfilepath" select="string-join((substring-before(base-uri(), '.xml'),'.csv'),'')"/>
    <xsl:template match="/">
        <xsl:text># generated from </xsl:text><xsl:value-of select="$filename" /><xsl:text> with csv2properties.xsl</xsl:text>
        <!--<xsl:value-of select="$csvfilepath" />-->
        <xsl:variable name="csvText" select="unparsed-text($csvfilepath)" />
        <xsl:for-each select="tokenize($csvText, '\n')">
            <!--<xsl:text>line: </xsl:text><xsl:sequence select="."/>-->
            <!--<xsl:text>analyze</xsl:text>-->
            <xsl:analyze-string select="." regex='^"?([^,^"]*)"?,"?([^,^"]*)"?,"?([^,^"]*)"?,"?([^,^"]*)"?,"?([^,^"]*)"?,"?([^,^"]*)$'>
                <xsl:matching-substring>
<xsl:text>language_name_</xsl:text><xsl:value-of select="regex-group(2)"/><xsl:text>=</xsl:text><xsl:value-of select="regex-group(1)"/><xsl:text>
</xsl:text>
<xsl:text>language_population_</xsl:text><xsl:value-of select="regex-group(2)"/><xsl:text>=</xsl:text><xsl:value-of select="regex-group(3)"/><xsl:text>
</xsl:text>
<xsl:text>language_area_</xsl:text><xsl:value-of select="regex-group(2)"/><xsl:text>=</xsl:text><xsl:value-of select="regex-group(4)"/><xsl:text>
</xsl:text>
<xsl:text>language_family_</xsl:text><xsl:value-of select="regex-group(2)"/><xsl:text>=</xsl:text><xsl:value-of select="regex-group(5)"/><xsl:text>
</xsl:text>
<xsl:text>language_dobes_</xsl:text><xsl:value-of select="regex-group(2)"/><xsl:text>=</xsl:text><xsl:value-of select="regex-group(6)"/><xsl:text>
</xsl:text>
                </xsl:matching-substring>
            </xsl:analyze-string>
        </xsl:for-each>
        <!--<xsl:for-each select="getTokens($lines[1])" as="xs:string+" />-->
    </xsl:template>
</xsl:stylesheet>
