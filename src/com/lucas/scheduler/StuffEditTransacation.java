package com.lucas.scheduler;

public interface StuffEditTransacation {
    public void onCategoryChanged(int CategoryId);
    public void onKindChanged(int KindId);
    public void onIntentChanged(int IntentId);
    public void onPressureChanged(int pressure);
    public void onImportanceChanged(int importance);
    public void onEmergencyChanged(int emergency);
    public void onDescriptionChanged(String des);
    public void onDeadLineChanged(String deadline);
}
