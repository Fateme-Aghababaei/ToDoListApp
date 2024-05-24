from rest_framework import serializers
from .models import Tag, Task

class TagSerializer(serializers.Serializer):
    title = serializers.CharField(required=False)
    id = serializers.IntegerField(required=False)

class TaskSerializer(serializers.Serializer):
    id = serializers.IntegerField(required=False)
    title = serializers.CharField(required=False)
    description = serializers.CharField(required=False)
    due_date = serializers.DateField(required=False)
    due_date_end = serializers.DateField(required=False)
    is_completed = serializers.BooleanField(required=False)
    priority = serializers.ChoiceField([(1, 'کم'), (2, 'متوسط'), (3, 'زیاد'), (0, 'مشخص‌نشده')], required=False)
    tags = TagSerializer(many=True, required=False)

class GetTagSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tag
        fields = '__all__'

class GetTaskSerializer(serializers.ModelSerializer):
    tags = GetTagSerializer(many=True)
    class Meta:
        model = Task
        fields = '__all__'