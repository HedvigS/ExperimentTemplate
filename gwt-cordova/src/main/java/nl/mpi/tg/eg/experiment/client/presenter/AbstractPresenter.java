/*
 * Copyright (C) 2014 Language In Interaction
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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.Messages;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.view.SimpleView;

/**
 * @since Oct 28, 2014 3:32:10 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AbstractPresenter implements Presenter {

    protected final Messages messages = GWT.create(Messages.class);
    protected final RootLayoutPanel widgetTag;
    final protected SimpleView simpleView;
    private PresenterEventListner backEventListner = null;
    private PresenterEventListner nextEventListner = null;

    public AbstractPresenter(RootLayoutPanel widgetTag, SimpleView simpleView) {
        this.widgetTag = widgetTag;
        this.simpleView = simpleView;
    }

    @Override
    public void setState(final AppEventListner appEventListner, final ApplicationState prevState, final ApplicationState nextState) {
        widgetTag.clear();
        if (prevState != null) {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button) {
                    appEventListner.requestApplicationState(prevState);
                }

                @Override
                public String getLabel() {
                    return prevState.label;
                }
            };
        } else {
            // todo: on android there needs to be a back action available
//            backEventListner = new PresenterEventListner() {
//
//                @Override
//                public void eventFired(ButtonBase button) {
//                    appEventListner.requestApplicationState(ApplicationState.end);
//                }
//
//                @Override
//                public String getLabel() {
//                    return ApplicationState.menu.label;
//                }
//            };
        }
        setTitle(backEventListner);
        if (nextState != null) {
            nextEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button) {
                    appEventListner.requestApplicationState(nextState);
                }

                @Override
                public String getLabel() {
                    return nextState.label;
                }
            };
            simpleView.setButton(SimpleView.ButtonType.next, nextEventListner);
        }
        setContent(appEventListner);
        simpleView.resizeView();
        widgetTag.add(simpleView);
    }

    @Override
    public void fireBackEvent() {
        if (backEventListner != null) {
            backEventListner.eventFired(null);
        }
    }

    @Override
    public void fireResizeEvent() {
        simpleView.resizeView();
    }

    protected abstract void setTitle(PresenterEventListner titleBarListner);

    protected abstract void setContent(final AppEventListner appEventListner);
}
