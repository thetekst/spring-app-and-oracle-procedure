package ru.tkachenko.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.SqlOutParameter
import org.springframework.jdbc.core.SqlParameter
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import spock.lang.Specification

import java.sql.Types

/**
 * @author d.tkachenko
 */
@SpringBootTest
class OracleProcedureTest extends Specification {

    @Autowired
    JdbcTemplate jdbcTemplate

    def "oracle procedure test"() {
        when:
        jdbcTemplate.setResultsMapCaseInsensitive(true)

        def simpleJdbcCall = (new SimpleJdbcCall(jdbcTemplate))
                .withNamedBinding()
                .withoutProcedureColumnMetaDataAccess()
                .withSchemaName("SUPER")
                .withProcedureName("someName")
                .useInParameterNames("param1")
                .declareParameters(
                        new SqlParameter("param1", Types.INTEGER),
                        new SqlOutParameter("param2", Types.INTEGER),
                )

        Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource()
                .addValue("param1", 5)
                .addValue("param2", 0)
        )

        then:
        result.param2 == 25
    }

}
