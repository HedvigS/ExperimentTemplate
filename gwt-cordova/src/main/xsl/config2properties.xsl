<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2properties.xsl
    Created on : June 25, 2015, 3:06 PM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Converts the textual components of the XML config file into a properties file.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:template match="/">
    <xsl:text>dataSubmitUrl=</xsl:text>
        <xsl:value-of select="experiment/@dataSubmitUrl" /><xsl:text>
</xsl:text>
    <xsl:text>localStorageName=</xsl:text>
        <xsl:value-of select="experiment/@localStorageName" /><xsl:text>
jsonDateFormat=yyyy-MM-dd HH:mm:ss vvvv
</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>errorScreenText={0}
stopSharingDetailsExplanation=({0}) and unique id ({1})
</xsl:text>
    </xsl:template>
    <xsl:template match="text()" /><!--prevent text nodes slipping into the output-->
    <xsl:template match="experiment/presenter">
        <xsl:text>menuLabel</xsl:text>
        <xsl:value-of select="@self" />
        <xsl:text>=</xsl:text>
        <xsl:value-of select="@menuLabel" />
        <xsl:text>
</xsl:text>        
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="*[@fieldName][. != '']">
        <xsl:value-of select="@fieldName" />
        <xsl:text>=</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>
</xsl:text>
    </xsl:template>
</xsl:stylesheet>
