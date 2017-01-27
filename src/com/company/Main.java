package com.company;

import com.facebook.ads.sdk.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static final String ACCESS_TOKEN = "EAANhNRjbwPcBAAZAzh57nrsritXAOZA0fkgqmmmbH9mZCZBhpCnZB7ZAE8L1v2OJ6UYc6UEy1i7JwEzKYZAR0J2L0MZCQZCoKc8vflZAVZAA6WgmK0mRlNdZAtIvnd1W2oD4caitgFbpr3Pg7aDo60lASMAqkYiMlV9ML7IZD";
    public static final String ACCOUNT_ID = "119327435221956";
    public static final String APP_SECRET = "67b0296ef3674302e87cb2f4f36a6a0d";
    public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(true);
    public static final File imageFile = new File("Assets/image.jpg");
    public static final String pageId = "1873874406165173";

    public static void main(String[] args) {
        try {
            AdAccount account = new AdAccount(ACCOUNT_ID, context);
            Targeting targeting = new Targeting()
                    .setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(Arrays.asList("US")))
                    .setFieldAgeMin(18L)
                    .setFieldAgeMax(30L)
                    .setFieldUserOs(Arrays.asList("Android", "iOS"));

            Campaign campaign = account.createCampaign()
                    .setName("Java SDK Test Carousel Campaign")
                    .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
                    .setSpendCap(10000L)
                    .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                    .execute();

            AdSet adset = account.createAdSet()
                    .setName("Java SDK Test Carousel AdSet")
                    .setCampaignId(campaign.getFieldId())
                    .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
                    .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                    .setDailyBudget(1000L)
                    .setBidAmount(100L)
                    .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_IMPRESSIONS)
                    .setTargeting(targeting)
                    .execute();

            AdImage image = account.createAdImage()
                    .addUploadFile("file", imageFile)
                    .execute();
            JsonArray childAttachments = new JsonArray();
            JsonObject attachment1 = new JsonObject();
            attachment1.addProperty("link", "https://www.example.com");
            attachment1.addProperty("description", "www.example.com");
            attachment1.addProperty("image_hash", image.getFieldHash());
            childAttachments.add(attachment1);
            JsonObject attachment2 = new JsonObject();
            attachment2.addProperty("link", "https://www.example.com");
            attachment2.addProperty("description", "www.example.com");
            attachment2.addProperty("image_hash", image.getFieldHash());
            childAttachments.add(attachment2);
            JsonObject attachment3 = new JsonObject();
            attachment3.addProperty("link", "https://www.example.com");
            attachment3.addProperty("description", "www.example.com");
            attachment3.addProperty("picture", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
            childAttachments.add(attachment3);

            AdCreative creative = account.createAdCreative()
                    .setTitle("Java SDK Test Carousel Creative")
                    .setBody("Java SDK Test Carousel Creative")
                    .setObjectStorySpec(new AdCreativeObjectStorySpec()
                            .setFieldLinkData(new AdCreativeLinkData()
                                    .setFieldChildAttachments(childAttachments.toString())
                                    .setFieldLink("www.example.com"))
                            .setFieldPageId(pageId)
                    )
                    .setLinkUrl("www.example.com")
                    .execute();

            Ad ad = account.createAd()
                    .setName("Java SDK Test Carousel ad")
                    .setAdsetId(Long.parseLong(adset.getId()))
                    .setCreative(creative)
                    .setStatus("PAUSED")
                    .setBidAmount(100L)
                    .setRedownload(true)
                    .execute();

        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}
