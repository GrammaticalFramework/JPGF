/* Language.java
 * Copyright (C) 2012 Grégoire Détrez
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework.examples.PhraseDroid;

import java.util.Locale;

public enum Language {
    GERMAN  ("PhrasebookGer", Locale.GERMAN),
    FRENCH  ("PhrasebookFre", Locale.FRENCH),
    ENGLISH ("PhrasebookEng", Locale.ENGLISH),
    SPANISH ("PhrasebookSpa", new Locale("es")),
    ITALIAN ("PhrasebookIta", Locale.ITALIAN);

    String concrete;
    Locale locale;
    Language(String concrete, Locale locale) {
        this.concrete = concrete;
        this.locale = locale;
    }

    public String getName() {
        return this.locale.getDisplayLanguage();
    }

    public Language[] getAvailableTargetLanguages() {
        Language[] ls = this.values();
        Language [] tls = new Language[4];
        int i = 0;
        for (Language l : ls)
            if (l != this) {
                tls[i] = l;
                i++;
            }
        return tls;
    }
    
    public Language getDefaultTargetLanguage() {
        switch (this) {
        case ENGLISH: return FRENCH;
        default: return ENGLISH;
        }
    }
    
    static public Language fromCode(String code) {
        if(code == null)
            return null;
        else if (code.equals("de"))
            return GERMAN;
        else if (code.equals("es"))
            return SPANISH;
        else if (code.equals("fr"))
            return FRENCH;
        else if (code.equals("it"))
            return ITALIAN;
        else if (code.equals("en"))
            return ENGLISH;
        else
            return null;
    }


    public String toString() {
	return this.getName();
    }

    static public int getPGFResource(Language l1, Language l2) {
        switch (l1) {
        case GERMAN: switch (l2) {
            case ENGLISH: return R.raw.phrasebook_de_en;
            case SPANISH: return R.raw.phrasebook_de_es;
            case FRENCH: return R.raw.phrasebook_de_fr;
            case ITALIAN: return R.raw.phrasebook_de_it;
            default: return -1;
            }
        case ENGLISH: switch (l2) {
            case GERMAN: return R.raw.phrasebook_de_en;
            case SPANISH: return R.raw.phrasebook_en_es;
            case FRENCH: return R.raw.phrasebook_en_fr;
            case ITALIAN: return R.raw.phrasebook_en_it;
            default: return -1;
            }
        case SPANISH: switch (l2) {
            case GERMAN: return R.raw.phrasebook_de_es;
            case ENGLISH: return R.raw.phrasebook_en_es;
            case FRENCH: return R.raw.phrasebook_es_fr;
            case ITALIAN: return R.raw.phrasebook_es_it;
            default: return -1;
            }
        case FRENCH: switch (l2) {
            case GERMAN: return R.raw.phrasebook_de_fr;
            case ENGLISH: return R.raw.phrasebook_en_fr;
            case SPANISH: return R.raw.phrasebook_es_fr;
            case ITALIAN: return R.raw.phrasebook_fr_it;
            default: return -1;
            }
        case ITALIAN: switch (l2) {
            case GERMAN: return R.raw.phrasebook_de_it;
            case ENGLISH: return R.raw.phrasebook_en_it;
            case SPANISH: return R.raw.phrasebook_es_it;
            case FRENCH: return R.raw.phrasebook_fr_it;
            default: return -1;
            }
        }
        return -1;
    }  
}

