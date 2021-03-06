/*
 * Copyright (C) 2015 Pivotal Software, Inc.
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
package nl.mpi.tg.eg.experimentdesigner.model;

/**
 * this can be updated with the output of: grep select=
 * ~/Documents/ExperimentTemplate/gwt-cordova/src/main/xsl/config2java.xsl
 *
 * @since Aug 18, 2015 4:51:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public enum FeatureAttribute {

    menuLabel,
    closeButtonLabel,
    back,
    next,
    fieldName,
    parameterName,
    linkedFieldName,
    code,
    codeFormat,
    validationRegex,
    allowedCharCodes,
    matchingRegex,
    src,
    link,
    percentOfPage,
    maxHeight,
    maxWidth,
    align,
    target,
    styleName,
    eventTier,
    filePerStimulus, // when recording audio this boolean determins if a separate recording should be made for each stimulus or one recording for the set of stimuli
    eventTag,
    ratingLabels,
    ratingLabelLeft,
    ratingLabelRight,
    sendData,
    networkErrorMessage,
    randomise,
    repeatCount,
    repeatRandomWindow,
    repeatIncorrect,
    hotKey,
    mp3,
    mp4,
    ogg,
    webm,
    wavFormat,
    poster,
    columnCount,
    kintypestring,
    diagramName,
    imageWidth,
    alternativeChoice,
    msToNext,
    msLabelFormat,
    animate, // animate currently has bounce stimuliCode or none
    minStimuliPerTag, // for each tag there should be at least N of each represented in the final list
    maxStimuliPerTag, // for each tag there should be no more than N of each represented in the final list
    maxStimuli,
    condition0Tag,
    condition1Tag,
    condition2Tag,
    scoreThreshold,
    showPlaybackIndicator,
    groupRole,
    groupMembers,
    groupCommunicationChannels,
    incrementPhase,
    scoreValue
}
