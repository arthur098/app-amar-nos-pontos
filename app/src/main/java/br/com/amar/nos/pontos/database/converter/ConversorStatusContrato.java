package br.com.amar.nos.pontos.database.converter;

import androidx.room.TypeConverter;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;

public class ConversorStatusContrato {

    @TypeConverter
    public EnumStatusContrato paraStatusContrato(Integer index) {
        return index != null ? EnumStatusContrato.values()[index] : EnumStatusContrato.PAGAMENTO_PENDENTE;
    }

    @TypeConverter
    public Integer paraInteger(EnumStatusContrato statusContrato) {
        return statusContrato.ordinal();
    }
}
