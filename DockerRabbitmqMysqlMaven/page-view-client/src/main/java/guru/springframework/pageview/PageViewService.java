package guru.springframework.pageview;

import guru.springframework.model.events.PageViewEvent;

/**
 * Created by jt on 2/25/17.
 */
public interface PageViewService {

    void sendPageViewEvent(PageViewEvent event);
}
