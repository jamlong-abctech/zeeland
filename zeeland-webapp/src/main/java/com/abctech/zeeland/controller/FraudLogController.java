package com.abctech.zeeland.controller;

import com.abctech.zeeland.form.data.AdObjectTransactionTypeItem;
import com.abctech.zeeland.form.data.ExtendedFraudBadWord;
import com.abctech.zeeland.form.data.ExtendedFraudLogObject;
import com.abctech.zeeland.form.util.CategoryBuilder;
import com.abctech.zeeland.form.util.PageCalculator;
import com.abctech.zeeland.form.util.PageInformation;
import com.abctech.zeeland.form.validation.SaveFraudBadWordValidation;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.transform.fraudlogobject.ExtendedFraudLogObjectListTransformer;
import no.zett.model.enums.AdObjectTransactionType;
import no.zett.service.facade.FraudBadWordResponse;
import no.zett.service.facade.FraudLogObjectResponse;
import no.zett.service.facade.FraudLogService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettFraudBadCategoryTransaction;
import no.zett.service.facade.ZettFraudBadWord;
import no.zett.service.facade.ZettFraudLogObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FraudLogController {

    private final static Logger log = LoggerFactory.getLogger(FraudLogController.class);

    private final static int RECORD_PER_PAGE = 20;
    private final static String UPDATEMESSAGE = "updateMessage";
    private final static String FRAUDBADWORD = "fraudBadWord";
    private final static String BADWORD = "badword";
    public static final String ERROR_MSG = "errorMsg";
    public static final String PROBLEM_UNDER_SAMTALER_TJENESTER = "Problem under samtaler tjenester";

    @Autowired
    private FraudLogService fraudLogService;

    @Autowired
    private CategoryBuilder categoryBuilder;

    @Autowired
    private SaveFraudBadWordValidation saveFraudBadWordValidation;

    @Autowired
    private WebserviceAuthentication webserviceAuthentication;

    @RequestMapping("fraudlog.html")
    public ModelAndView showFraudLog(@RequestParam(value = UPDATEMESSAGE, required = false) String updateMessage,
                                     @RequestParam(value = "page", required = false) String page) throws ServiceException_Exception {

        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(fraudLogService);

        int totalRecord = fraudLogService.numberAdsWaiting().getAdWaitingSize();
        modelAndView.addObject("totalFraudList", totalRecord);
        PageCalculator pageCalculator = new PageCalculator();
        PageInformation pageInformation = pageCalculator.calculate(page, totalRecord);
        modelAndView.addObject("currentPage", pageInformation.getCurrentPage());
        modelAndView.addObject("totalPage", pageInformation.getTotalPage());
        int endRecord = pageInformation.getEndRecord();
        int startRecord = pageInformation.getStartRecord();

        FraudLogObjectResponse fraudObjectResponse = fraudLogService.getFraudLogObjectsWaitingPage(startRecord, endRecord);
        List<ZettFraudLogObject> zettFraudLogObjectList = fraudObjectResponse.getZettFraudLogObjects();
        ZettFraudLogObject[] zettFraudLogObjects = new ZettFraudLogObject[zettFraudLogObjectList.size()];
        ExtendedFraudLogObjectListTransformer extendedFraudLogObjectListTransformer = new ExtendedFraudLogObjectListTransformer();
        List<ExtendedFraudLogObject> extendedFraudLogObjectList = extendedFraudLogObjectListTransformer.transform(zettFraudLogObjectList.toArray(zettFraudLogObjects));

        modelAndView.addObject("fraudLogObjectList", extendedFraudLogObjectList);
        if (updateMessage != null && !updateMessage.isEmpty()) {
            modelAndView.addObject(UPDATEMESSAGE, updateMessage);
        }
        modelAndView.setViewName("fraudlog");
        return modelAndView;
    }

    @RequestMapping("blockfrauduser.html")
    public ModelAndView blockUser(@RequestParam(value = "useremail", required = true) String userEmail) throws ServiceException_Exception {
        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(fraudLogService);
        fraudLogService.blockUser(userEmail);
        modelAndView.addObject(UPDATEMESSAGE, "fraudlog.update.success");
        modelAndView.setViewName("redirect:fraudlog.html");
        return modelAndView;
    }

    @RequestMapping("fraudadprocess.html")
    public ModelAndView fraudAdProcess(@RequestParam(value = "hdaprrovelist", required = true) String approveList,
                                       @RequestParam(value = "hddenylist", required = true) String denyList,
                                       @RequestParam(value = "hdblocklist", required = true) String emailList) throws ServiceException_Exception {

        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(fraudLogService);
        fraudLogService.updateState(approveList, denyList, "no");
        fraudLogService.blockUser(emailList);
        modelAndView.addObject(UPDATEMESSAGE, "fraudlog.update.success");
        modelAndView.setViewName("redirect:fraudlog.html");
        return modelAndView;

    }

    @RequestMapping("fraudedit.html")
    public ModelAndView fraudWordEdit(@RequestParam(value = UPDATEMESSAGE, required = false) String updateMessage,
                                      @RequestParam(value = "page", required = false) String page) throws ServiceException_Exception, ParseException {
        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(fraudLogService);
        FraudBadWordResponse fraudBadWordResponse = fraudLogService.getFraudBadWords();
        List<ZettFraudBadWord> fraudBadWordList = fraudBadWordResponse.getZettFraudBadWords();
        int totalRecord = fraudBadWordList.size();
        modelAndView.addObject("totalFraudList", totalRecord);

        PageCalculator pageCalculator = new PageCalculator();
        PageInformation pageInformation = pageCalculator.calculate(page, totalRecord);
        modelAndView.addObject("currentPage", pageInformation.getCurrentPage());
        modelAndView.addObject("totalPage", pageInformation.getTotalPage());
        int endRecord = pageInformation.getEndRecord();
        int startRecord = pageInformation.getStartRecord();

        List<ZettFraudBadWord> displayWordList = new ArrayList<ZettFraudBadWord>();
        for (int i = startRecord; i < endRecord; i++) {
            displayWordList.add(fraudBadWordList.get(i));
        }

        List<ExtendedFraudBadWord> extendedFraudBadWordList = new ArrayList<ExtendedFraudBadWord>();
        for (ZettFraudBadWord zettFraudBadWord : displayWordList) {
            ExtendedFraudBadWord extendedFraudBadWord = new ExtendedFraudBadWord();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String date = dateFormat.format(zettFraudBadWord.getAddedTime().toGregorianCalendar().getTime());
            extendedFraudBadWord.setNewBadWord(zettFraudBadWord.getBadWord());
            extendedFraudBadWord.setBadWord(zettFraudBadWord.getBadWord());
            extendedFraudBadWord.setCreatedTime(date);
            extendedFraudBadWordList.add(extendedFraudBadWord);
        }
        modelAndView.addObject("fraudBadWordList", extendedFraudBadWordList);
        if (updateMessage != null && !updateMessage.isEmpty()) {
            modelAndView.addObject(UPDATEMESSAGE, updateMessage);
        }

        ExtendedFraudBadWord fraudBadWord = new ExtendedFraudBadWord();
        modelAndView.addObject(FRAUDBADWORD, fraudBadWord);

        modelAndView.setViewName("fraudedit");
        return modelAndView;
    }

    @RequestMapping("addbadword.html")
    public ModelAndView addNewBadWord() {
        ModelAndView modelAndView = new ModelAndView();
        ExtendedFraudBadWord fraudBadWord = new ExtendedFraudBadWord();
        modelAndView.addObject(FRAUDBADWORD, fraudBadWord);
        modelAndView.setViewName(BADWORD);
        return modelAndView;
    }

    @RequestMapping("deletebadword.html")
    public ModelAndView deleteBadWord(@RequestParam(value = "word", required = true) String deleteBadWord) throws ServiceException_Exception {
        ModelAndView modelAndView = new ModelAndView();
        log.debug("try to delete the word .... ");
        try {
            log.debug("delete Badword with word =" + deleteBadWord);
            webserviceAuthentication.authentication(fraudLogService);
            fraudLogService.deleteBadWord(deleteBadWord);
            modelAndView.addObject(UPDATEMESSAGE, "fruadedit.update.success");
        } catch (ServiceException_Exception err) {
            log.debug("can not delete for word = " + deleteBadWord + " :" + err.getMessage());
        }
        modelAndView.setViewName("redirect:fraudedit.html");
        return modelAndView;

    }

    @RequestMapping("editbadword.html")
    public ModelAndView editBadWord(@RequestParam(value = "word", required = true) String badWord) throws ServiceException_Exception {
        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(fraudLogService);
        FraudBadWordResponse fraudBadWordResponse = fraudLogService.getFraudBadWord(badWord);
        ZettFraudBadWord zettFraudBadWord = fraudBadWordResponse.getZettFraudBadWord();
        ExtendedFraudBadWord extendedFraudBadWord = new ExtendedFraudBadWord();
        extendedFraudBadWord.setNewBadWord(zettFraudBadWord.getBadWord());
        extendedFraudBadWord.setBadWord(zettFraudBadWord.getBadWord());
        modelAndView.addObject(FRAUDBADWORD, extendedFraudBadWord);
        modelAndView.setViewName(BADWORD);
        return modelAndView;
    }

    @RequestMapping("savebadword.html")
    public ModelAndView saveBadWord(@ModelAttribute(FRAUDBADWORD) ExtendedFraudBadWord fraudBadWord,
                                    BindingResult result) throws ServiceException_Exception {
        ModelAndView modelAndView = new ModelAndView();
        saveFraudBadWordValidation.validate(fraudBadWord, result);
        if (!result.hasErrors()) {
            webserviceAuthentication.authentication(fraudLogService);
            if (fraudBadWord.getBadWord().isEmpty()) {
                //add new word
                log.debug("... try to add new bad word.");
                try {
                    fraudLogService.addBadWord(fraudBadWord.getNewBadWord());
                    modelAndView.addObject(UPDATEMESSAGE, "fruadedit.add.success");
                } catch (ServiceException_Exception err) {
                    ObjectError objectError = new ObjectError("newBadWord", err.getMessage());
                    result.addError(objectError);
                    modelAndView.setViewName("badword");
                }
            } else {
//                FraudBadWordResponse fraudBadWordResponse = fraudLogService.getFraudBadWord(fraudBadWord.getBadWord());
//                ZettFraudBadWord zettFraudBadWord = fraudBadWordResponse.getZettFraudBadWord();
                log.debug("... try to update old word = " + fraudBadWord.getBadWord() + " with " + fraudBadWord.getNewBadWord());
                if (!fraudBadWord.getBadWord().equalsIgnoreCase(fraudBadWord.getNewBadWord())) {
                    if (fraudBadWord.getNewBadWord() != null && !fraudBadWord.getNewBadWord().isEmpty()) {
                        fraudLogService.deleteBadWord(fraudBadWord.getBadWord());
                        fraudLogService.addBadWord(fraudBadWord.getNewBadWord());
                        modelAndView.addObject(UPDATEMESSAGE, "fruadedit.update.success");
                    }
                }
            }
            modelAndView.setViewName("redirect:fraudedit.html");
        } else {
            log.debug("in this case ....");
            modelAndView.addObject(FRAUDBADWORD, fraudBadWord);
            modelAndView.setViewName(BADWORD);
        }
        return modelAndView;
    }

    @RequestMapping("updatebadword.html")
    public ModelAndView updateBadWord(@RequestParam(value = "oldword", required = true) String oldWord,
                                      @RequestParam(value = "newword", required = true) String newWord) {
        ModelAndView modelAndView = new ModelAndView();
        String returnMessage = oldWord;
        if (!oldWord.isEmpty() && !newWord.isEmpty()) {
            log.debug("******* old word = " + oldWord);
            log.debug("******* new word = " + newWord);
            try {
                webserviceAuthentication.authentication(fraudLogService);
                fraudLogService.deleteBadWord(oldWord);
                fraudLogService.addBadWord(newWord);
                returnMessage = newWord;
                modelAndView.addObject(UPDATEMESSAGE, "fruadedit.update.success");
            } catch (ServiceException_Exception err) {
                log.warn("can not update new badword with oldword = " + oldWord + ", new word = " + newWord + " : " + err.getMessage());
            }
        }
        modelAndView.addObject("result", returnMessage);
        modelAndView.setViewName("ajaxresponse");
        return modelAndView;
    }

    @RequestMapping("categoryedit.html")
    public ModelAndView listFraudBadCategoryTransaction(HttpServletRequest request) {
        log.info("listFraudBadCategoryTransaction controller in action.");

        ModelAndView modelAndView = new ModelAndView();

        // Authenticat web service
        webserviceAuthentication.authentication(fraudLogService);
        List<ZettFraudBadCategoryTransaction> zettFraudBadCategoryTransactionsList = null;
        try {
            zettFraudBadCategoryTransactionsList = fraudLogService.listAllFraudBadCategoryTransaction();
        } catch (ServiceException_Exception err) {
            // Has problem while call zservices
            modelAndView.addObject(ERROR_MSG, PROBLEM_UNDER_SAMTALER_TJENESTER);
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }
        if(zettFraudBadCategoryTransactionsList != null) {
            modelAndView.addObject("totalFraudList", zettFraudBadCategoryTransactionsList.size());
        }
        modelAndView.addObject("zettFraudBadCategoryTransactionsList", zettFraudBadCategoryTransactionsList);

        // list all values in AdObjectTransactionTypeItem enum and convert for display in view.
        AdObjectTransactionType[] values = AdObjectTransactionType.values();
        List<AdObjectTransactionTypeItem> adObjectTransactionTypeItemsList = new ArrayList<AdObjectTransactionTypeItem>();
        AdObjectTransactionTypeItem transactionTypeItem = null;
        for(AdObjectTransactionType item : values) {
            transactionTypeItem = new AdObjectTransactionTypeItem();
            transactionTypeItem.setTextValue(item.toTextValue());
            transactionTypeItem.setValue(item.toInteger());
            adObjectTransactionTypeItemsList.add(transactionTypeItem);
        }

        modelAndView.addObject("categoryMap", categoryBuilder.getCategoryMap());
        modelAndView.addObject("adObjectTransactionTypeItemsList", adObjectTransactionTypeItemsList);
        modelAndView.setViewName("categoryedit");
        modelAndView.addObject(ERROR_MSG, request.getParameter(ERROR_MSG));
        modelAndView.addObject("updateMessage", request.getParameter(UPDATEMESSAGE));

        return modelAndView;
    }

    @RequestMapping("savecategory.html")
    public ModelAndView saveCategory(@RequestParam(value = "hdcategoy", required = true) Integer categoryId,
                                     @RequestParam(value = "transactionId", required = true) Integer transactionId) {

        log.info("saveCategory in action.");
        ModelAndView modelAndView = new ModelAndView();
        // validate categoryId and transactionId are required
        if(categoryId == null || transactionId == null) {
            modelAndView.addObject(ERROR_MSG, "Kan ikke legge kategori");
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }

        // Authenticat web service
        webserviceAuthentication.authentication(fraudLogService);
        List<ZettFraudBadCategoryTransaction> zettFraudBadCategoryTransactionsList = null;
        try {
            // get all FraudBadCategoryTransactions for check duplicate
            zettFraudBadCategoryTransactionsList = fraudLogService.listAllFraudBadCategoryTransaction();
            if(!checkBadCategoryTransactionDuplicate(zettFraudBadCategoryTransactionsList, categoryId, transactionId)) {
                fraudLogService.addFraudBadCategoryTransaction(categoryId, transactionId);
            } else {
                // found duplicate
                modelAndView.addObject(ERROR_MSG, "Kan ikke legge duplikat kategori");
                modelAndView.setViewName("redirect:categoryedit.html");
                return modelAndView;
            }
        } catch (ServiceException_Exception e) {
            // Has problem while call zservices
            modelAndView.addObject(ERROR_MSG, PROBLEM_UNDER_SAMTALER_TJENESTER);
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }

        modelAndView.addObject(UPDATEMESSAGE, "categoryedit.add");
        modelAndView.setViewName("redirect:categoryedit.html");
        return modelAndView;
    }

    @RequestMapping("editcategory.html")
    public ModelAndView editCategory(@RequestParam(value = "hdcategoy", required = true) Integer categoryId,
                                     @RequestParam(value = "transactionId", required = true) Integer transactionId,
                                     @RequestParam(value = "rowNumber", required = false) Integer rowNumber) {

        log.info("saveCategory in action.");
        ModelAndView modelAndView = new ModelAndView();
        // validate categoryId and transactionId are required
        if(categoryId == null || transactionId == null) {
            modelAndView.addObject(ERROR_MSG, "Kan ikke legge kategori");
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }

        // Authenticat web service
        webserviceAuthentication.authentication(fraudLogService);
        List<ZettFraudBadCategoryTransaction> zettFraudBadCategoryTransactionsList = null;
        try {
            zettFraudBadCategoryTransactionsList = fraudLogService.listAllFraudBadCategoryTransaction();

            //delete old row
            if(checkBadCategoryTransactionDuplicate(zettFraudBadCategoryTransactionsList, categoryId, transactionId)) {
                // found duplicate
                modelAndView.addObject(ERROR_MSG, "Kan ikke legge duplikat kategori");
                modelAndView.setViewName("redirect:categoryedit.html");
                return modelAndView;
            }
            ZettFraudBadCategoryTransaction categoryTransactionForDelete = zettFraudBadCategoryTransactionsList.get(rowNumber);
            fraudLogService.deleteFraudBadCategoryTransaction(categoryTransactionForDelete.getCategoryId(),categoryTransactionForDelete.getAdObjectTransactionType());

            fraudLogService.addFraudBadCategoryTransaction(categoryId, transactionId);
        } catch (ServiceException_Exception e) {
            // Has problem while call zservices
            modelAndView.addObject(ERROR_MSG, PROBLEM_UNDER_SAMTALER_TJENESTER);
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }

        modelAndView.addObject(UPDATEMESSAGE, "categoryedit.add");
        modelAndView.setViewName("redirect:categoryedit.html");
        return modelAndView;
    }

    @RequestMapping("deletecategorytransaction.html")
    public ModelAndView deleteCategory(@RequestParam(value = "rowNumberForDelete", required = false) Integer rowNumber) {
        log.info("deleteCategory in action.");
        ModelAndView modelAndView = new ModelAndView();

        webserviceAuthentication.authentication(fraudLogService);
        List<ZettFraudBadCategoryTransaction> zettFraudBadCategoryTransactionsList = null;
        try {
            zettFraudBadCategoryTransactionsList = fraudLogService.listAllFraudBadCategoryTransaction();
            ZettFraudBadCategoryTransaction categoryTransaction = zettFraudBadCategoryTransactionsList.get(rowNumber);
            if(categoryTransaction != null) {
                fraudLogService.deleteFraudBadCategoryTransaction(categoryTransaction.getCategoryId(), categoryTransaction.getAdObjectTransactionType());
            }
        } catch (ServiceException_Exception e) {
            // Has problem while call zservices
            modelAndView.addObject(ERROR_MSG, PROBLEM_UNDER_SAMTALER_TJENESTER);
            modelAndView.setViewName("redirect:categoryedit.html");
            return modelAndView;
        }

        modelAndView.addObject(UPDATEMESSAGE, "categoryedit.deleted");
        modelAndView.setViewName("redirect:categoryedit.html");
        return modelAndView;
    }

    private boolean checkBadCategoryTransactionDuplicate(List<ZettFraudBadCategoryTransaction> badCategoryTransactionsList
            , Integer categoryId, Integer transactionId) {

        for(ZettFraudBadCategoryTransaction zettFraudBadCategoryTransaction : badCategoryTransactionsList) {
            if (zettFraudBadCategoryTransaction.getAdObjectTransactionType().equals(transactionId) &&
                    zettFraudBadCategoryTransaction.getCategoryId().equals(categoryId)) {
                return true;
            }
        }
        return false;
    }
}
