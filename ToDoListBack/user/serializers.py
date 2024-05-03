from django.contrib.auth.models import User
from .models import Profile
from rest_framework import serializers

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = '__all__'

class UserSerializer(serializers.ModelSerializer):
    profile = ProfileSerializer()
    class Meta:
        model = User
        fields = ['username', 'email', 'first_name', 'profile']

class LoginSerializer(serializers.Serializer):
    email = serializers.CharField()
    password = serializers.CharField()

class EditUserSerializer(serializers.Serializer):
    username = serializers.CharField()
    first_name = serializers.CharField()

class ChangePasswordSerializer(serializers.Serializer):
    old_pass = serializers.CharField()
    new_pass = serializers.CharField()