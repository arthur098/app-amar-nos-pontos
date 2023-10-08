package br.com.amar.nos.pontos.database.converter;

import androidx.room.TypeConverter;
import br.com.amar.nos.pontos.enumerator.EnumEstadoCivil;

public class ConversorEstadoCivil {

    @TypeConverter
    public EnumEstadoCivil paraEstadoCivil(Integer index) {
        return index != null ? EnumEstadoCivil.values()[index] : EnumEstadoCivil.SOLTEIRO;
    }

    @TypeConverter
    public Integer paraInteger(EnumEstadoCivil enumEstadoCivil) {
        return enumEstadoCivil.ordinal();
    }
}
