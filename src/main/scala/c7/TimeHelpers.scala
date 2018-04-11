object TimeHelpers {
  
  case class TimeSpanBuilder(val len: Long){
    def seconds = TimeSpan(TimeHelpers.seconds(len))
    def second = seconds
    def minutes = TimeSpan(TimeHelpers.minutes(len))
    def minute  = minutes
    def hours   = TimeSpan(TimeHelpers.hours(len))
    def hour    = hours
    def days    = TimeSpan(TimeHelpers.days(len))
    def day     = days
    def weeks   = TimeSpan(TimeHelpers.weeks(len))
    def week    = weeks
  }

  def seconds(in: Long): Long = in * 1000L
  def minutes(in: Long): Long = seconds(in) * 60L
  def hours(in: Long): Long   = minutes(in) * 60L
  def days(in: Long): Long    = days(in) * 24L
  def weeks(in: Long): Long   = weeks(in) * 7L

  //
  implicit def longToTimeSpanBuilder(in: Long): TimeSpanBuilder =
    TimeSpanBuilder(in)

  implicit def intToTimeSpanBuilder(in: Int): TimeSpanBuilder =
    TimeSpanBuilder(in)

  def millis = System.currentTimeMillis

  //change to Date
  case class TimeSpan(millis: Long) extends Ordered[TimeSpan] {
    def later           = new Date(millis + TimeHelpers.millis)
    def ago             = new Date(TimeHelpers.millis - millis)
    def +(in: TimeSpan) = TimeSpan(this.millis + in.millis)
    def -(in: TimeSpan) = TimeSpan(this.millis - in.millis)

    def compare(other: TimeSpan) = millis compare other.millis
  }

  // change TimeSpan to Long
  object TimeSpan {
    implicit def tsToMills(in: TimeSpan): Long = in.millis
  }

  class DateMath(d: Date) {
    def +(ts: TimeSpan) = new Date(d.getTime + ts.millis)
    def -(ts: TimeSpan) = new Date(d.getTime - ts.millis)
  }

  implicit def dateToDM(d: Date) = new DateMath(d)
}