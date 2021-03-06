package me.xdrop.jrand.generators.money

import me.xdrop.jrand.JRand
import org.joda.time.format.DateTimeFormat

class ExpiryDateGeneratorTest extends GroovyTestCase {
    def instance = { -> JRand.expiryDate() }

    void testExpired() {
        def exp = DateTimeFormat.forPattern("MM/yy").parseDateTime(instance().expired().gen())
        assertTrue exp.isBeforeNow()
    }

    void testNonExpired() {
        def exp = DateTimeFormat.forPattern("MM/yy").parseDateTime(instance().gen())
        assertFalse exp.isBeforeNow()
    }

    void testCanExpire() {
        def parser = DateTimeFormat.forPattern("MM/yy")
        boolean before = false
        boolean after = false
        100.times({
            def time = parser.parseDateTime(instance().canExpire(true).gen())
            if (time.isBeforeNow()) {
                before = true
            } else if (time.isAfterNow()) {
                after = true
            }
        })
        assertTrue before && after
    }

    void testLongversion() {
        assertFalse instance().gen().length() == 7
        assertTrue instance().longVersion().gen().length() == 7
        assertTrue DateTimeFormat.forPattern("MM/yyyy").parseDateTime(instance().longVersion().gen()) != null
    }

    void testPrint() {
        println instance().gen()
        println instance().expired().gen()
        println instance().longVersion().gen()
    }
}
