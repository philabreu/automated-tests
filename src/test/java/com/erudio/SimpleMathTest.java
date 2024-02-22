package com.erudio;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Teste de operações matematicas")
@TestMethodOrder(MethodOrderer.Random.class)
public class SimpleMathTest {

    private SimpleMath simpleMath;

    @BeforeEach
    public void init() {
        simpleMath = new SimpleMath();
    }

    @Test
    @DisplayName("deve testar soma")
    public void shouldSum() {
        assertEquals(8.2d, simpleMath.sum(6.2d, 2d));
    }

    @RepeatedTest(3)
    @DisplayName("deve lançar exceção ao dividir por 0")
    public void shouldThrowArithmeticException() {
        double first = 6.2d;
        double second = 0d;

        assertThrows(ArithmeticException.class, () -> {
            simpleMath.division(first, second);
        });
    }

    @Test
    @DisplayName("deve testar subtração")
    public void shouldSubtraction() {
        assertEquals(4.2d, simpleMath.subtraction(6.2d, 2d));
    }

    @Test
    @DisplayName("deve testar multiplicação")
    public void shouldMultiplication() {
        assertEquals(6d, simpleMath.multiplication(2d, 3d));
    }

    @Test
    @DisplayName("deve testar divisão")
    public void shouldDivision() {
        assertEquals(3d, simpleMath.division(9d, 3d));
    }

    @Test
    @DisplayName("deve testar media aritmetica")
    public void shouldMean() {
        assertEquals(10d, simpleMath.mean(10d, 10d));
    }

    @Test
    @DisplayName("deve calcular raiz quadrada")
    public void shouldSquareRoot() {
        assertEquals(2d, simpleMath.squareRoot(4d));
    }


    @ParameterizedTest
    @MethodSource("shouldDivisionWithParams")
    public void shouldDivisionWithParams(double first, double second, double expected) {
        Double actual = simpleMath.division(first, second);

        assertEquals(expected,
                actual,
                () -> first + "/" + second + "não retornou" + expected);
    }

    public static Stream<Arguments> shouldDivisionWithParams() {
        return Stream.of(
                Arguments.of(6.2d, 2d, 3.1d),
                Arguments.of(70.0d, 10.0d, 7.0d),
                Arguments.of(18.0d, 6.0d, 3.0d)
        );
    }


}