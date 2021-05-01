package fr.foacs.mc.lyscore.api;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerHelper {

  final Logger log;

  public LoggerHelper(final Logger log) {
    this.log = log;
  }

  /**
   * Log a SEVER message formatted.
   * SEVERE is a message level indicating a serious failure.
   *
   * @see Level#SEVERE
   * @param message The message to log
   */
  public void sever(final String message) {
    this.log.severe(message);
  }

  /**
   * Log a SEVER message formatted.
   * SEVERE is a message level indicating a serious failure.
   *
   * @see Level#SEVERE
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void sever(final String format, Object... params) {
    if (this.log.isLoggable(Level.SEVERE)) {
      this.log.severe(String.format(format, params));
    }
  }

  /**
   * Log a SEVER message formatted.
   * SEVERE is a message level indicating a serious failure.
   *
   * @see Level#SEVERE
   * @param exception The exception thrown
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void sever(final Exception exception, final String format, Object... params) {
    if (this.log.isLoggable(Level.SEVERE)) {
      this.log.log(Level.SEVERE, String.format(format, params), exception);
    }
  }

  /**
   * Log a WARNING message formatted.
   * WARNING is a message level indicating a potential problem.
   *
   * @see Level#WARNING
   * @param message The message to log
   */
  public void warning(final String message) {
      this.log.warning(message);
  }

  /**
   * Log a WARNING message formatted.
   * WARNING is a message level indicating a potential problem.
   *
   * @see Level#WARNING
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void warning(final String format, Object... params) {
    if (this.log.isLoggable(Level.WARNING)) {
      this.log.warning(String.format(format, params));
    }
  }
  /**
   * Log a INFO message formatted.
   * INFO is a message level for informational messages.
   *
   * @see Level#INFO
   * @param message The message to log
   */
  public void info(final String message) {
    this.log.info(message);
  }

  /**
   * Log a INFO message formatted.
   * INFO is a message level for informational messages.
   *
   * @see Level#INFO
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void info(final String format, Object... params) {
    if (this.log.isLoggable(Level.INFO)) {
      this.log.info(String.format(format, params));
    }
  }


  /**
   * Log a CONFIG message formatted.
   * CONFIG is a message level for static configuration messages.
   *
   * @see Level#CONFIG
   * @param message The message to log
   */
  public void config(final String message) {
    this.log.config(message);
  }

  /**
   * Log a CONFIG message formatted.
   * CONFIG is a message level for static configuration messages.
   *
   * @see Level#CONFIG
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void config(final String format, Object... params) {
    if (this.log.isLoggable(Level.CONFIG)) {
      this.log.config(String.format(format, params));
    }
  }

  /**
   * Log a FINE message formatted.
   * FINE is a message level providing tracing information.
   *
   * @see Level#FINE
   * @param message The message to log
   */
  public void fine(final String message) {
    this.log.fine(message);
  }

  /**
   * Log a FINE message formatted.
   * FINE is a message level providing tracing information.
   *
   * @see Level#FINE
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void fine(final String format, Object... params) {
    if (this.log.isLoggable(Level.FINE)) {
      this.log.fine(String.format(format, params));
    }
  }

  /**
   * Log a FINER message formatted.
   *
   * @see Level#FINER
   * @param message The message to log
   */
  public void finer(final String message) {
    this.log.finer(message);
  }

  /**
   * Log a FINER message formatted.
   *
   * @see Level#FINER
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void finer(final String format, Object... params) {
    if (this.log.isLoggable(Level.FINER)) {
      this.log.finer(String.format(format, params));
    }
  }


  /**
   * Log a FINEST message formatted.
   *
   * @see Level#FINEST
   * @param message The message to log
   */
  public void finest(final String message) {
    this.log.finest(message);
  }
  /**
   * Log a FINEST message formatted.
   *
   * @see Level#FINEST
   * @param format A format string
   * @param params Arguments referenced by the format specifiers in the format string.
   */
  public void finest(final String format, Object... params) {
    if (this.log.isLoggable(Level.FINEST)) {
      this.log.finest(String.format(format, params));
    }
  }

}
