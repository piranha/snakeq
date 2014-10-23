# Qsnake

That's a basic "Snake" game written in ClojureScript. To run install
[Leiningen](http://leiningen.org/#install) and then run `make`.

It will tell you to open `http://localhost:3449/` in the browser. Do that.

Change `src/snake/core.cljs` when figwheel is running and observe it being
immediately executed by browser (immediate means it takes 0.1-0.3s to compile
and then loads new code in your browser without changing state). Source maps are
enabled so you're all set.

There is also advanced compilation configured, so you can do `make www` and it
will put optimized version inside of `www` directory (be patient, it can take
half a minute depending on your machine).

There is also [an article](http://solovyov.net/en/2014/cljs-start/) describing
whole setup.
