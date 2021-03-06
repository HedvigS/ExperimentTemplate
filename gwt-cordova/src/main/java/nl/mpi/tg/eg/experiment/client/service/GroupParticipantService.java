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

import com.google.gwt.user.client.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.listener.TimedStimulusListener;

/**
 * @since Nov 8, 2016 1:47:57 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupParticipantService {

//    private final HashMap<String, ArrayList<TimedStimulusListener>> selfActivityListeners = new HashMap<>();
//    private final HashMap<String, ArrayList<TimedStimulusListener>> othersActivityListeners = new HashMap<>();
    private final HashMap<String, TimedStimulusListener> activityListeners = new HashMap<>();
    private final TimedStimulusListener screenResetRequestListner;
    private final TimedStimulusListener stimulusSyncListner;
    private final TimedStimulusListener groupInfoChangeListner;
    private final String allMemberCodes;
    private final String groupCommunicationChannels;
    private final TimedStimulusListener connectedListener;
    private final TimedStimulusListener groupNotReadyListener;
    private boolean isConnected = false;
    private TimedStimulusListener lastFiredListner = null;
    private String lastFiredListnerGroupRole = null;

    private final String userId;
    private final String screenId;
    private String userLabel = null;
//    private final String allMemberCodes = null;
    private String memberCode = null;
    private String groupId = null;
    private String stimulusId = null;
    private String stimuliListLoaded;
    private String stimuliListGroup;
    private Integer stimulusIndex = null;
    private Integer requestedPhase = 0;
    private String messageSenderId = null;
    private String messageSenderMemberCode = null;
    private String messageString = null;
    private Boolean groupReady = false;
//    private Boolean userIdMatches = false;
    private String responseStimulusOptions = null;
    private String responseStimulusId = null;
    private String groupScore = null;
    private String groupUUID = null;

    public GroupParticipantService(final String userId, String screenId, String groupMembers, String groupCommunicationChannels, String stimuliListLoaded, TimedStimulusListener connectedListener, TimedStimulusListener groupNotReadyListener, TimedStimulusListener screenResetRequestListner, TimedStimulusListener stimulusSyncListner, TimedStimulusListener groupInfoChangeListner) {
        this.userId = userId;
        this.allMemberCodes = groupMembers;
        this.lastFiredListnerGroupRole = groupMembers; // the first connection should require all members to respond
        this.groupCommunicationChannels = groupCommunicationChannels;
        this.connectedListener = connectedListener;
        this.groupNotReadyListener = groupNotReadyListener;
        this.screenResetRequestListner = screenResetRequestListner;
        this.groupInfoChangeListner = groupInfoChangeListner;
        this.stimulusSyncListner = stimulusSyncListner;
        this.screenId = screenId;
        this.stimuliListLoaded = stimuliListLoaded;
    }

//    public void addGroupActivity(final String groupRole, final int requestedPhase, final TimedStimulusListener activityListener) {
//
//        ArrayList<TimedStimulusListener> currentSelfRoles = selfActivityListeners.get(groupRole);
//        ArrayList<TimedStimulusListener> currentOthersRoles = othersActivityListeners.get(groupRole);
//        if (currentSelfRoles == null) {
//            currentSelfRoles = new ArrayList<>();
//            selfActivityListeners.put(groupRole, currentSelfRoles);
//        }
//        if (currentOthersRoles == null) {
//            currentOthersRoles = new ArrayList<>();
//            othersActivityListeners.put(groupRole, currentOthersRoles);
//        }
//        while (currentSelfRoles.size() < requestedPhase) {
//            currentSelfRoles.add(null);
//        }
//        while (currentOthersRoles.size() < requestedPhase) {
//            currentOthersRoles.add(null);
//        }
//        switch (groupMessageMatch) {
//            case self:
//                currentSelfRoles.add(requestedPhase, activityListener);
//                break;
//            case other:
//                currentOthersRoles.add(requestedPhase, activityListener);
//                break;
//            case all:
//                currentSelfRoles.add(requestedPhase, activityListener);
//                currentOthersRoles.add(requestedPhase, activityListener);
//                break;
//        }
//    }
    public void addGroupActivity(final String groupRole, final TimedStimulusListener activityListener) {
        activityListeners.put(groupRole, activityListener);
    }

    protected void clearLastFiredListner() {
        this.lastFiredListner = null;
        this.lastFiredListnerGroupRole = null;
    }

    protected synchronized void handleGroupMessage(String userId, String screenId, String userLabel, String groupId, String allMemberCodes,
            String memberCode,
            String originMemberCode,
            String expectedRespondents,
            String actualRespondents,
            String stimulusId, String stimulusIndex, String stimuliListGroup, String requestedPhase,
            String messageString, Boolean groupReady, String responseStimulusId, String groupScore, String groupUUID) {
        final boolean userIdMatches = this.userId.equals(userId);
        final boolean screenIdMatches = this.screenId.equals(screenId);
        final boolean groupIdMatches = (this.groupId != null) ? this.groupId.equals(groupId) : true; // if a group id was provided then ignore anyother groups
        if (userIdMatches && screenIdMatches) {
            final boolean userGroupLabelUpdateNeeded = (userIdMatches && screenIdMatches) && (this.memberCode == null && memberCode != null);
            // handle direct messages
            this.userLabel = userLabel;
            this.memberCode = memberCode;
            this.groupId = groupId;
            this.stimuliListGroup = stimuliListGroup;
            if (userGroupLabelUpdateNeeded) {
                groupInfoChangeListner.postLoadTimerFired();
            }
            if (!this.stimuliListLoaded.equals(this.stimuliListGroup)) {
                // if the stimuli list does not match then reset the page after storing the received stimuli list
                screenResetRequestListner.postLoadTimerFired();
                return;
            }
        }

        if (groupIdMatches && screenIdMatches) {
            // handle group messages
            this.messageSenderMemberCode = originMemberCode;
            this.groupUUID = groupUUID;
            this.groupReady = groupReady;
            if (this.stimuliListLoaded.equals(this.stimuliListGroup)) {
                // only if the group is ready do we try to process the group message
                boolean messageIsRelevant = false;
                for (String channel : groupCommunicationChannels.split("\\|")) {
                    final List<String> channelList = Arrays.asList(channel.split(","));
                    // check communication channel before responding to the message
                    if (channelList.contains(this.memberCode) && channelList.contains(this.messageSenderMemberCode)) {
                        final List<String> expectedRespondentsList = Arrays.asList(expectedRespondents.split(","));
                        final List<String> requiredRespondentsList = new ArrayList<>(expectedRespondentsList);
                        requiredRespondentsList.retainAll(channelList);
                        final List<String> actualRespondentsList = Arrays.asList(actualRespondents.split(","));
                        if (actualRespondentsList.containsAll(requiredRespondentsList)) {
                            messageIsRelevant = true;
                        }
                    }
                }
                if (messageIsRelevant) {
                    // make sure that all relevent members have responded before moving to the next phase
                    final int currentRequestedPhase = Integer.parseInt(requestedPhase);
                    for (String groupRole : activityListeners.keySet()) {
                        final String[] splitRole = groupRole.split(":");
                        int roleIndex = currentRequestedPhase % splitRole.length;
                        if (splitRole[roleIndex].contains(this.memberCode)) {
                            final TimedStimulusListener currentListner = activityListeners.get(groupRole);
//                        ((userIdMatches) ? selfActivityListeners : othersActivityListeners).get(groupRole).get(this.requestedPhase).postLoadTimerFired();
                            if (splitRole.length == 1 /* if there is only one role to this screen then it is ok to refire the last */
                                    || (lastFiredListner == null || !lastFiredListner.equals(currentListner))) {
                                this.stimulusId = stimulusId;
                                this.stimulusIndex = Integer.parseInt(stimulusIndex); // todo check for double adding of stimulus index and or something like that
                                this.requestedPhase = currentRequestedPhase;
                                this.messageString = messageString;
                                this.messageSenderId = userId;
                                this.responseStimulusId = responseStimulusId;
                                this.groupScore = groupScore;
                                stimulusSyncListner.postLoadTimerFired();
                                currentListner.postLoadTimerFired();
                                lastFiredListner = currentListner;
                                lastFiredListnerGroupRole = splitRole[roleIndex];
                            }
                        }
                    }
                }
            } else {
                groupNotReadyListener.postLoadTimerFired();
            }
        }
    }

    protected void setConnected(Boolean isConnected) {
        this.isConnected = isConnected;
        connectedListener.postLoadTimerFired();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public String getAllMemberCodes() {
        return allMemberCodes;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getStimulusId() {
        return stimulusId;
    }

    public String getGroupCommunicationChannels() {
        return groupCommunicationChannels;
    }

    public Integer getStimulusIndex() {
        return stimulusIndex;
    }

    public String getStimuliListLoaded() {
        return stimuliListLoaded;
    }

    public void setStimuliListLoaded(String stimuliListLoaded) {
        this.stimuliListLoaded = stimuliListLoaded;
    }

    public String getStimuliListGroup() {
        return stimuliListGroup;
    }

    public Integer getRequestedPhase() {
        return requestedPhase;
    }

    public String getMessageString() {
        return messageString;
    }

    public String getMessageSenderId() {
        return messageSenderId;
    }

    public String getResponseStimulusOptions() {
        return responseStimulusOptions;
    }

    public void setResponseStimulusOptions(String responseStimulusOptions) {
        this.responseStimulusOptions = responseStimulusOptions;
    }

    public String getResponseStimulusId() {
        return responseStimulusId;
    }

    public void setResponseStimulusId(String responseStimulusId) {
        this.responseStimulusId = responseStimulusId;
    }

    public String getGroupScore() {
        return groupScore;
    }

    public String getGroupUUID() {
        return groupUUID;
    }

    public boolean isGroupReady() {
        return groupReady;
    }

    public String getMessageSenderMemberCode() {
        return messageSenderMemberCode;
    }

    public native void joinGroupNetwork(String groupServerUrl) /*-{
        var groupParticipantService = this;
//        console.log("joinGroupNetwork: " + groupServerUrl + " : " + groupName);
        
        var socket = new $wnd.SockJS(groupServerUrl + 'gs-guide-websocket');
        stompClient = $wnd.Stomp.over(socket);
        stompClient.connect(
            {
//            withCredentials: false,
//            noCredentials : true
            },
            function (frame) {
            groupParticipantService.@nl.mpi.tg.eg.experiment.client.service.GroupParticipantService::setConnected(Ljava/lang/Boolean;)(@java.lang.Boolean::TRUE);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/shared/group', function (groupMessage) {
                var contentData = JSON.parse(groupMessage.body);
                console.log('contentData: ' + contentData);
                groupParticipantService.@nl.mpi.tg.eg.experiment.client.service.GroupParticipantService::handleGroupMessage(
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/Boolean;
            Ljava/lang/String;
            Ljava/lang/String;
            Ljava/lang/String;
            )(
            contentData.userId, 
            contentData.screenId,
            contentData.userLabel,
            contentData.groupId,
            contentData.allMemberCodes, 
            contentData.memberCode, 
            contentData.originMemberCode, 
            contentData.expectedRespondents,
            contentData.actualRespondents,
            contentData.stimulusId,
            Number(contentData.stimulusIndex),
            contentData.stimuliList, 
            Number(contentData.requestedPhase), 
            contentData.messageString,
            (contentData.groupReady)?@java.lang.Boolean::TRUE : @java.lang.Boolean::FALSE, 
            contentData.responseStimulusId,
            contentData.groupScore,
            contentData.groupUUID
            );
//            }, function(error) {
//                // display the error's message header:
//                alert(error.headers.message);
            });
        });
     }-*/;

    public void messageGroup(int incrementPhase, String stimulusId, String stimulusIndex, String messageString, String responseStimulusOptions, String responseStimulusId, int memberScore) {
        String windowGroupId = Window.Location.getParameter("group");
        if (windowGroupId == null) {
            windowGroupId = groupId;
        }
        messageGroup(this.requestedPhase + incrementPhase, userId, windowGroupId, screenId, allMemberCodes, groupCommunicationChannels, lastFiredListnerGroupRole, stimulusId, stimulusIndex, stimuliListLoaded, messageString, responseStimulusOptions, responseStimulusId, memberScore);
    }

    private native void messageGroup(int requestedPhase, String userId, String groupId, String screenId, String allMemberCodes, String groupCommunicationChannels, String expectedRespondents, String stimulusId, String stimulusIndex, String stimuliList, String messageString, String responseStimulusOptions, String responseStimulusId, int memberScore) /*-{
    var groupParticipantService = this;
    stompClient.send("/app/group", {}, JSON.stringify({
        'userId': userId,
        'groupId': groupId,
        'screenId': screenId,
        'userLabel': null,
        'allMemberCodes': allMemberCodes,
        'groupCommunicationChannels': groupCommunicationChannels,
        'expectedRespondents': expectedRespondents,
        'memberCode': null,
        'originMemberCode': null,
        'stimulusId': stimulusId,
        'requestedPhase': requestedPhase,
        'stimulusIndex': stimulusIndex,
        'stimuliList': stimuliList,
        'messageString': messageString,
        'responseStimulusOptions': responseStimulusOptions,
        'responseStimulusId': responseStimulusId,
        'memberScore': memberScore,
        'groupReady': null
    }));
    }-*/;
}
