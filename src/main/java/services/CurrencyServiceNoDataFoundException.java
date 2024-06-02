package services;

public class CurrencyServiceNoDataFoundException extends Exception
{
    public CurrencyServiceNoDataFoundException(String message)
    {
        super(message);
    }
}
