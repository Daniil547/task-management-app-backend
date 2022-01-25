package io.github.daniil547.common.repositories.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This is ugly but necessary because {@link BeanPropertySqlParameterSource}
 * is unable to map snake_case properties to camelCase,
 * even though its complementary {@link BeanPropertyRowMapper} class can (this is dumb).</p>
 *
 * <p>Also it seems that default converter is unable to map enums to strings (this is duuumb).
 * The workaround is to manually set type of all the enum parameters to varchar.
 * Either this or you have to implement your own {@link org.springframework.core.convert.converter.ConverterFactory}
 * and then register it into {@link org.springframework.core.convert.support.DefaultConversionService}
 * which is ridiculously verbose and simply ugly.</p>
 *
 * <p>All the inherited methods copy those of {@link BeanPropertySqlParameterSource}
 * but format {@code paramNames} to camelCase via {@link #camelifyName(String)}.</p>
 */
public class CaseInsensitiveBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {
    private String[] propertyNames;
    private BeanWrapper beanWrapper;

    /**
     * Create a new CaseInsensitiveBeanPropertySqlParameterSource for the given bean.
     *
     * @param object the bean instance to wrap
     */
    public CaseInsensitiveBeanPropertySqlParameterSource(Object object) {
        super(object);

        this.registerSqlType("visibility", 12);
        this.beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
    }

    /**
     * Convert a name from snake_case to camelCase.
     * Any "_x" is converted into "X".
     * Trailing underscore is removed ("...x_" -> "...x")
     *
     * @param name the original name
     * @return the converted name
     */
    protected String camelifyName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                if (i != name.length() - 1) {
                    result.append(Character.toUpperCase(name.charAt(i + 1)));
                    i = i + 1;
                } // if the underscore is the last character just don't append it
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }


    @Override
    public String[] getReadablePropertyNames() {
        //String[] propertyNames = super.getReadablePropertyNames();
        if (this.propertyNames == null) {
            List<String> names = new ArrayList<>();
            PropertyDescriptor[] props = this.beanWrapper.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (this.beanWrapper.isReadableProperty(pd.getName())) {
                    String camelCaseName = camelifyName(pd.getName());
                    names.add(camelCaseName);
                }
            }
            this.propertyNames = StringUtils.toStringArray(names);
        }
        return this.propertyNames;
    }

    @Override
    public boolean hasValue(String paramName) {
        return this.beanWrapper.isReadableProperty(camelifyName(paramName));
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        try {
            return this.beanWrapper.getPropertyValue(camelifyName(paramName));
        } catch (NotReadablePropertyException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public int getSqlType(String paramName) {
        String camelCaseName = camelifyName(paramName);

        int sqlType = super.getSqlType(camelCaseName);
        if (sqlType != TYPE_UNKNOWN) {
            return sqlType;
        }
        Class<?> propType = this.beanWrapper.getPropertyType(camelCaseName);
        return StatementCreatorUtils.javaTypeToSqlParameterType(propType);
    }
}
