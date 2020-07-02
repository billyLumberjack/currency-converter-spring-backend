package com.exmachina.sam.currency.tests.conversion;

import com.exmachina.sam.currency.conversion.CurrenciesConversion;

import java.util.Comparator;

public class IgnoreCoefficientCurrenciesConversionComparator implements Comparator<CurrenciesConversion>
{
    public int compare(CurrenciesConversion a, CurrenciesConversion b)
    {
        int comparisonResult;

        comparisonResult = a.getSourceAmount().compareTo(b.getSourceAmount());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getDestinationAmount().compareTo(b.getDestinationAmount());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getUsedRate().getSource().compareTo(b.getUsedRate().getSource());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getUsedRate().getDestination().compareTo(b.getUsedRate().getDestination());

        return comparisonResult;

    }
}