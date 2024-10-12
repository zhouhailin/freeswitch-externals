package link.thingscloud.freeswitch.cdr.controller;

import link.thingscloud.freeswitch.cdr.service.CdrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>CdrController class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@RestController
@RequestMapping("/freeswitch")
public class CdrController {

    @Autowired
    private CdrService cdrService;

    /**
     * <p>cdr.</p>
     *
     * @param httpHeaders a {@link org.springframework.http.HttpHeaders} object.
     * @param reqText     a {@link String} object.
     */
    @RequestMapping("/cdr")
    public void cdr(@RequestHeader HttpHeaders httpHeaders, @RequestBody String reqText) {
        log.debug("cdr httpHeaders : [{}]", httpHeaders);
        log.debug("cdr reqText     : [{}]", reqText);
        cdrService.handle(reqText);
    }

}
