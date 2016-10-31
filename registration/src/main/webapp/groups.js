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
/*
 * @since Oct 26, 2016 2:08:45 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */

var stompClient = null;
var userId = Math.floor((1 + Math.random()) * 0x10000);

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#greetings").html("");
    $("#groupTarget").html("");
    $("#animateTarget").html("");
    if (connected) {
        $("#conversation").show();
        $("#groupTarget").append("<tr><td>userId</td><td>Label</td><td>Group</td><td>MemberCodes</td><td>Code</td><td>stimulusId</td><td>message</td><td>Ready</td></tr>");
    } else {
        $("#conversation").hide();
    }
}

function connect() {
    var socket = new SockJS('/synquiz2-admin/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        var progressDivBar = $("<div style='background: grey;' class='progressDivBar'>&nbsp;</div>");
        $("#animateTarget").append(progressDivBar);
        stompClient.subscribe('/shared/animation', function (greeting) {
            $("#startBar").prop("disabled", true);
            var contentData = JSON.parse(greeting.body);
            showData(contentData);

            progressDivBar.css("width", contentData.width + "px");
//            $("#send").hide();
        });
        stompClient.subscribe('/shared/group', function (greeting) {
            var contentData = JSON.parse(greeting.body);
//            console.log('greeting.body: ' + greeting.body);
//            console.log('contentData: ' + contentData);
            var usersTableRow = $("#userId" + contentData.userId);
            if (!usersTableRow.length) {
                $("#groupTarget").append("<tr id=\"userId" + contentData.userId + "\"></tr>");
                usersTableRow = $("#userId" + contentData.userId);
            }
            var usersTableCells = "<td>" + contentData.userId + "</td><td>" + contentData.userLabel + "</td><td>" + contentData.groupId + "</td><td>" + contentData.allMemberCodes + "</td><td>" + contentData.memberCode + "</td><td>" + contentData.stimulusId + "</td><td>" + contentData.messageString + "</td><td>" + contentData.groupReady + "</td>";
            usersTableRow.html(usersTableCells);

//            var groupMemberDiv = $("<div style='background: grey;' class='progressDivBar'>&nbsp;</div>");
//            $("#groupTarget").append(groupMemberDiv);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    $("#groupTarget").append("<tr><td>Disconnected</td></tr>");
}
var stepCounter = 0;

function startBar() {
    stompClient.send("/app/shared", {}, JSON.stringify({
        'text': '',
        'fill': '',
        'cx': '',
        'width': stepCounter,
        'height': '',
        'left': '',
        'top': ''
    }));
    stepCounter++;
    if (stepCounter > 300)
        stepCounter = 0;
    setTimeout(startBar, 10);
}
function updateGroup() {
    stompClient.send("/app/group", {}, JSON.stringify({
        'userId': userId,
        'userLabel': null,
        'allMemberCodes': 'A,B,C,D,E,F,G',
        'memberCode': null,
        'stimulusId': Math.floor((1 + Math.random()) * 0x10000),
        'messageString': $("#messageString").val(),
        'groupReady': null
    }));
}

function showData(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#startBar").click(function () {
        startBar();
    });
    $("#join").click(function () {
        updateGroup();
    });
    $("#messageString").change(function () {
        updateGroup();
    });
});