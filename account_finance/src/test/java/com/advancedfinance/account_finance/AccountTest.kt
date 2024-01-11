package com.advancedfinance.account_finance

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class AccountTest {

    @Test
    fun `deve retornar verdadeiro quando startedbalance for valido`() {
        //Configuração (Criar uma account)
        val contaValida = AccountModel(
            id = 1,
            name = "Dinheiro",
            startedBalance = BigDecimal(9.99),
            accountType = AccountTypeModel(
                id = 1,
                name = "Receita"
            )
        )

        //Ação (verificar se o startedBalance é valido)
        val startedBalanceEhValido = contaValida.startedBalanceEhValido

        //Afirmação (verificar se o resultado é o que se espera)
        startedBalanceEhValido.shouldBeTrue()
    }

    @Test
    fun `deve retornar falso quando startedbalance for maior que cem`() {
        //Configuração (Criar uma account)
        val contaComStartedBalanceMaiorQueCem = AccountModel(
            id = 1,
            name = "Dinheiro",
            startedBalance = BigDecimal(200),
            accountType = AccountTypeModel(
                id = 1,
                name = "Receita"
            )
        )

        //Ação (verificar se o startedBalance é valido)
        val startedBalanceEhValido = contaComStartedBalanceMaiorQueCem.startedBalanceEhValido

        //Afirmação (verificar se o resultado é o que se espera)
        startedBalanceEhValido.shouldBeFalse()
    }

    @Test
    fun `deve retornar falso quando startedbalance for igual a zero`() {
        val contaComStartedBalanceIgualAZero = AccountModel(
            id = 1,
            name = "Dinheiro",
            startedBalance = BigDecimal(0),
            accountType = AccountTypeModel(
                id = 1,
                name = "Receita"
            )
        )

        val contaComStartedBalanceMenorQueZero = AccountModel(
            id = 1,
            name = "Dinheiro",
            startedBalance = BigDecimal(-10.99),
            accountType = AccountTypeModel(
                id = 1,
                name = "Receita"
            )
        )

        //Ação (verificar se o startedBalance é valido)
        val startedBalanceIgualAZeroEhValido = contaComStartedBalanceIgualAZero.startedBalanceEhValido
        val startedBalanceMenorQueZeroEhValido = contaComStartedBalanceMenorQueZero.startedBalanceEhValido

        //Afirmação (verificar se o resultado é o que se espera)
        startedBalanceIgualAZeroEhValido.shouldBeFalse()
        startedBalanceMenorQueZeroEhValido.shouldBeFalse()
    }
}