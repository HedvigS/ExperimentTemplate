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
package nl.ru.languageininteraction.language.client.registration;

/**
 * @since Oct 29, 2014 11:23:33 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class HighScoreException extends Exception {

    private final ErrorType errorType;

    public enum ErrorType {

        non202response,
        buildererror,
        connectionerror
    }

    public HighScoreException(ErrorType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    public HighScoreException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}