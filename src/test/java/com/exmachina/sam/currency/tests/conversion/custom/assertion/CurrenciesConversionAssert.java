package com.exmachina.sam.currency.tests.conversion.custom.assertion;

import com.exmachina.sam.currency.conversion.CurrenciesConversion;

/**
 * {@link CurrenciesConversion} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractCurrenciesConversionAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class CurrenciesConversionAssert extends AbstractCurrenciesConversionAssert<CurrenciesConversionAssert, CurrenciesConversion> {

  /**
   * Creates a new <code>{@link CurrenciesConversionAssert}</code> to make assertions on actual CurrenciesConversion.
   * @param actual the CurrenciesConversion we want to make assertions on.
   */
  public CurrenciesConversionAssert(CurrenciesConversion actual) {
    super(actual, CurrenciesConversionAssert.class);
  }

  /**
   * An entry point for CurrenciesConversionAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myCurrenciesConversion)</code> and get specific assertion with code completion.
   * @param actual the CurrenciesConversion we want to make assertions on.
   * @return a new <code>{@link CurrenciesConversionAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static CurrenciesConversionAssert assertThat(CurrenciesConversion actual) {
    return new CurrenciesConversionAssert(actual);
  }
}
