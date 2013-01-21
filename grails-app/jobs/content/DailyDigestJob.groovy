package content


class DailyDigestJob {
    def timeout = 1000 * 60 * 5 // execute job once in 5 seconds
	def startDelay = 1000000 * 60

    def execute() {
        // log.info "Start daily digest job"
    }
}
