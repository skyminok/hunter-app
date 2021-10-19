import {ApplicationRef, NgModuleRef} from '@angular/core';
import {createNewHosts} from '@angularclass/hmr';

export const hmrBootstrap = (module: any, bootstrap: () => Promise<NgModuleRef<any>>) => {
    let ngModule: NgModuleRef<any>;
    // noinspection TypeScriptUnresolvedVariable,JSIgnoredPromiseFromCall
    module.hot.accept();
    bootstrap().then(mod => ngModule = mod);
    // noinspection TypeScriptUnresolvedVariable
    module.hot.dispose(() => {
        const appRef: ApplicationRef = ngModule.injector.get(ApplicationRef);
        const elements = appRef.components.map(c => c.location.nativeElement);
        const makeVisible = createNewHosts(elements);
        ngModule.destroy();
        makeVisible();
    });
};
