dev:
	lein figwheel dev

clean:
	lein cljsbuild clean

www: www/qsnake.min.js www/style.css

www/qsnake.min.js: $(shell find src -name '*.cljs') project.clj
	lein cljsbuild once www

www/%.css: resources/public/css/%.css
	@mkdir -p $(@D)
	cp $< $@
