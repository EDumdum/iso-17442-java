package org.edumdum.iso;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.function.Function;

import org.edumdum.iso.Iso7064;
import org.junit.Test;

public class Iso17442Test
{
	private static final String EXCEPTION_TEMPLATE_ISVALID = "Invalid data format; expecting '^[0-9A-Z]{18}[0-9]{2}$', found: '%s'.";
	
	private static final HashMap<String, ExceptionDefinition> EXCEPTION_ISVALID = new HashMap<String,ExceptionDefinition>();
	static {
		EXCEPTION_ISVALID.put(null, new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_ISVALID, "null")));
		EXCEPTION_ISVALID.put("", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_ISVALID, "")));
		EXCEPTION_ISVALID.put("0123456", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_ISVALID, "0123456")));
        EXCEPTION_ISVALID.put("969500T3MBS4SQAMHJ4A", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_ISVALID, "969500T3MBS4SQAMHJ4A")));
        EXCEPTION_ISVALID.put("969500T3MBS4SQAMHJ455", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_ISVALID, "969500T3MBS4SQAMHJ455")));
	}

	private static final HashMap<String, Boolean> DATA_ISVALID = new HashMap<String, Boolean>();
	static
	{
		DATA_ISVALID.put("969500T3MBS4SQAMHJ45", false);
        DATA_ISVALID.put("469500KSV493XWY0PS33", false);
        DATA_ISVALID.put("7245005WBNJAF0BD0S30", false);
        DATA_ISVALID.put("7245O0VKKSH9QOLTFR81", false);
        DATA_ISVALID.put("724500884QS64MG71N60", false);
        DATA_ISVALID.put("969500T3M8S4SQAMHJ45", true);
        DATA_ISVALID.put("969500KSV493XWY0PS33", true);
        DATA_ISVALID.put("7245005WBNJAFHBD0S30", true);
        DATA_ISVALID.put("724500VKKSH9QOLTFR81", true);
        DATA_ISVALID.put("724500884QS64MG71N64", true);
	}

	private static final String EXCEPTION_TEMPLATE_GENERATE = "Invalid data format; expecting '^[0-9A-Z]{18}$', found: '%s'.";	

	private static final HashMap<String, ExceptionDefinition> EXCEPTION_GENERATE = new HashMap<String, ExceptionDefinition>();
	static {
		EXCEPTION_GENERATE.put(null, new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_GENERATE, "null")));
		EXCEPTION_GENERATE.put("", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_GENERATE, "")));
		EXCEPTION_GENERATE.put("0123456", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_GENERATE, "0123456")));
		EXCEPTION_GENERATE.put("969500T3MBS4SQAMHJ4A", new ExceptionDefinition(IllegalArgumentException.class, String.format(EXCEPTION_TEMPLATE_GENERATE, "969500T3MBS4SQAMHJ4A")));
	}

	private static final HashMap<String, String> DATA_GENERATE = new HashMap<String, String>();
	static
	{
		DATA_GENERATE.put("969500T3M8S4SQAMHJ", "969500T3M8S4SQAMHJ45");
        DATA_GENERATE.put("969500KSV493XWY0PS", "969500KSV493XWY0PS33");
        DATA_GENERATE.put("7245005WBNJAFHBD0S", "7245005WBNJAFHBD0S30");
        DATA_GENERATE.put("724500VKKSH9QOLTFR", "724500VKKSH9QOLTFR81");
        DATA_GENERATE.put("724500884QS64MG71N", "724500884QS64MG71N64");
	}

	@Test
	public final void testIsValid()
	{
		assertException(Iso17442::isValid, EXCEPTION_ISVALID);
		assertResult(Iso17442::isValid, DATA_ISVALID);
	}

	@Test
	public final void testComputeWithoutCheck()
	{
		assertException(Iso17442::generate, EXCEPTION_GENERATE);
		assertResult(Iso17442::generate, DATA_GENERATE);
	}

	private static <T> void assertException(Function<T, ?> function, HashMap<T, ExceptionDefinition> exceptions) {
		for (T input: exceptions.keySet()) {
			assertException(function, input, exceptions.get(input));
		}
	}

	private static <T> void assertException(Function<T, ?> function, T input, ExceptionDefinition exceptionDefinition)
	{
		try
		{
			function.apply(input);
			fail(String.format("Expecting an exception of type '%s' with message '%s' to be thrown.", exceptionDefinition.getExceptionClass().getName(), exceptionDefinition.getExceptionMessage()));
		}
		catch (Exception exception)
		{
			if (!exceptionDefinition.getExceptionClass().isInstance(exception))
			{
				fail(String.format("Invalid exception type; expecting: '%s', found: '%s'.", exceptionDefinition.getExceptionClass(), exception.getClass().getName()));
			}
			else if (exceptionDefinition.getExceptionMessage() != null && !exceptionDefinition.getExceptionMessage().equals(exception.getMessage()))
			{
				fail(String.format("Invalid exception message; expecting: '%s', found: '%s'.", exceptionDefinition.getExceptionMessage(), exception.getMessage()));
			}
		}
	}

	private static <T, R> void assertResult(Function<T, R> function, HashMap<T, R> data) {
		for (T input : data.keySet())
		{
			assertResult(function, input, data.get(input));
		}
	}

	private static <T, R> void assertResult(Function<T, R> function, T input, R response) {
		R result = function.apply(input);

		if (!response.equals(result)) {
			fail(String.format("Invalid response for input '%s'; expecting: '%d', found: '%d'.", input, response, result));
		}
	}

	private static class ExceptionDefinition {
		private Class<?> exceptionClass;
		private String exceptionMessage;

		ExceptionDefinition(Class<?> exceptionClass, String exceptionMessage) {
			this.exceptionClass = exceptionClass;
			this.exceptionMessage = exceptionMessage;
		}

		public Class<?> getExceptionClass() {
			return this.exceptionClass;
		}

		public String getExceptionMessage() {
			return this.exceptionMessage;
		}
	}
}
