import {browser, by, element} from 'protractor';

export class AppPage {
    // noinspection JSMethodCanBeStatic
    navigateTo(): Promise<unknown> {
        return browser.get(browser.baseUrl) as Promise<unknown>;
    }

    // noinspection JSMethodCanBeStatic
    getTitleText(): Promise<string> {
        return element(by.css('app-root .content #title')).getText() as Promise<string>;
    }
}
