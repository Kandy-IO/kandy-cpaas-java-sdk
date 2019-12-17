package com.cpaassdk;

import okhttp3.mockwebserver.MockWebServer;

public interface Sequence {
  void execute(Api api, MockWebServer server);
}
