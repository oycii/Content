
class XmlPhones {
static def PHONES = '''
<phones>
<phone id="1377" vendor="Alcatel" model="Alcatel Mandarina Duck"/>
<phone id="128" vendor="Alcatel" model="Alcatel OT 332"/>
<phone id="2239" vendor="Alcatel" model="Alcatel OT 363"/>
<phone id="139" vendor="Alcatel" model="Alcatel OT 535"/>
<phone id="311" vendor="Alcatel" model="Alcatel OT 556"/>
<phone id="1296" vendor="BenQ" model="BenQ C30"/>
<phone id="1931" vendor="BenQ" model="BenQ C36"/>
</phones>
  '''
  
	static void main (args) {
		println "Start test"
		def phones = new XmlSlurper().parseText(XmlPhones.PHONES)
		def allRecords = phones.phone
		// println phones.phone.@id records.car.'*'.collect{ it.name()[0..1] }.join('-')
		println phones.phone.@id.collect{ it.text() }.join(',')
	}
}
