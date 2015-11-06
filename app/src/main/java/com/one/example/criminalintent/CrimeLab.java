package com.one.example.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> crimes;

    public static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    public void add(Crime crime) {
        crimes.add(crime);
    }

    private CrimeLab(Context context) {
        crimes = new ArrayList<>();
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : crimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
