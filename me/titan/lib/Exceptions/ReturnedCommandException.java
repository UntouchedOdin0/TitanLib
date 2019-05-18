package me.titan.lib.Exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReturnedCommandException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public final String tellMessage;
}
