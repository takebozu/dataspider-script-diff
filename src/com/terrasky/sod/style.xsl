<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="children" >
		<xsl:element name="{name()}">
			<xsl:attribute name="type">
				<xsl:value-of select="@type" />
			</xsl:attribute>
			<xsl:for-each select="memento">
				<xsl:sort select="./@id" data-type="text" order="ascending" />
				<xsl:copy-of select="." />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>                

	<xsl:template match="entries" >
		<xsl:element name="{name()}">
			<xsl:attribute name="type">
				<xsl:value-of select="@type" />
			</xsl:attribute>
			<xsl:for-each select="entry">
				<xsl:sort select="./@key" data-type="text" order="ascending" />
				<xsl:copy-of select="." />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>                

                
	<xsl:template match="node()|@*" >
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>