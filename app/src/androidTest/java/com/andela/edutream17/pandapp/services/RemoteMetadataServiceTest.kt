package com.andela.edutream17.pandapp.services

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteMetadataServiceTest {

    @Test
    fun getAllModelsMetadata() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val remoteModelService = RemoteModelService.build(context)
        var size = 0
        remoteModelService.getAllModelsMetadata()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getModelMetadata() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val remoteModelService = RemoteModelService.build(context)
        //advanceUntilIdle()
        val cm = remoteModelService.getModelMetadata("PandApp-T4")
        assertEquals("Panda", cm.metadata?.label)
    }
}