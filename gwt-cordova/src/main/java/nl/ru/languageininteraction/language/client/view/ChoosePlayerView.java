/*
 * Copyright (C) 2015 Language In Interaction
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
package nl.ru.languageininteraction.language.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import nl.ru.languageininteraction.language.client.ChoosePlayerScreenBuilder;
import nl.ru.languageininteraction.language.client.exception.AudioException;
import nl.ru.languageininteraction.language.client.listener.PresenterEventListner;
import nl.ru.languageininteraction.language.client.service.AudioPlayer;

/**
 * @since Feb 4, 2015 1:20:10 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class ChoosePlayerView extends AbstractSvgView {

    protected final ChoosePlayerScreenBuilder svgBuilder = new ChoosePlayerScreenBuilder();
    protected final PresenterEventListner editButtonListner;
    protected final PresenterEventListner goButtonListner;
    protected final PresenterEventListner createButtonListner;
    protected final PresenterEventListner switchButtonListner;

    public ChoosePlayerView(PresenterEventListner editButtonListner, PresenterEventListner goButtonListner, PresenterEventListner createButtonListner, PresenterEventListner switchButtonListner, AudioPlayer audioPlayer) throws AudioException {
        super(audioPlayer);
        this.editButtonListner = editButtonListner;
        this.goButtonListner = goButtonListner;
        this.createButtonListner = createButtonListner;
        this.switchButtonListner = switchButtonListner;
    }

    @Override
    protected void getSvg(SafeHtmlBuilder builder) {
        svgBuilder.getSvg(builder);
    }

    @Override
    protected boolean performClick(final String svgGroupStateString) {
        boolean consumed = false;
        label.setText(svgGroupStateString);
        ChoosePlayerScreenBuilder.SvgGroupStates svgGroup = ChoosePlayerScreenBuilder.SvgGroupStates.valueOf(svgGroupStateString);
        switch (svgGroup) {
            case EditButton:
                consumed = true;
                editButtonListner.eventFired(null);
                break;
            case GoButton:
                consumed = true;
                goButtonListner.eventFired(null);
                break;
            case NameField:
                consumed = true;
                break;
            case NewPlayerButton:
                consumed = true;
                createButtonListner.eventFired(null);
                break;
            case SwitchPlayerButton:
                consumed = true;
                switchButtonListner.eventFired(null);
                break;
        }
        return consumed;
    }

    @Override
    public void showAudioEnded() {
    }
}
