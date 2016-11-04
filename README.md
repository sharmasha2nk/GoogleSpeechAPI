# GoogleSpeechAPI
This is a sample repo for accessing the [Google Cloud Speech REST API](https://cloud.google.com/speech/reference/rest). Currently APIs are in Beta. Please see Google Cloud Platform Launch Stages.

## Prerequisites

### Enable the Speech API

If you have not already done so, [enable the Google Cloud Speech API for your project](https://console.developers.google.com/apis/api/speech.googleapis.com/overview).
You must be whitelisted to do this.


### Download and install Java and Maven

Install [Java7 or
higher](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html).

This sample uses the [Apache Maven][maven] build system. Before getting started, be
sure to [download][maven-download] and [install][maven-install] it. When you use
Maven as described here, it will automatically download the needed client
libraries.

[maven]: https://maven.apache.org
[maven-download]: https://maven.apache.org/download.cgi
[maven-install]: https://maven.apache.org/install.html


### Set Up to Authenticate With Your Project's Credentials

The example uses a API Key for authentication.
So next, set up to authenticate with the Speech API using your project's
API key.

Visit the [Cloud Console](https://console.developers.google.com), and navigate to:
`API Manager > Credentials > Create credentials > API key`

Set the GOOGLE_API_KEY in env or in Main class. 

For more details about authentication check [Authenticating to the API](https://cloud.google.com/speech/docs/common/auth).







