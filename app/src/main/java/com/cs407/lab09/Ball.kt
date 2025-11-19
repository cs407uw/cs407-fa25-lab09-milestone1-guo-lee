package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // TODO: Call reset()
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // Old acceleration (a0) = accX/accY
        // New acceleration (a1) = xAcc/yAcc
        val a0x = accX
        val a1x = xAcc
        val a0y = accY
        val a1y = yAcc

        // ---- X axis ----
        val newVx = velocityX + 0.5f * (a0x + a1x) * dT          // Eq. (1)
        val dx = velocityX * dT + (3f * a0x + a1x) * dT * dT / 6f // Eq. (2)

        // ---- Y axis ----
        val newVy = velocityY + 0.5f * (a0y + a1y) * dT
        val dy = velocityY * dT + (3f * a0y + a1y) * dT * dT / 6f

        // Update position
        posX += dx
        posY += dy

        // Update velocity
        velocityX = newVx
        velocityY = newVy

        // Store latest acceleration for next step
        accX = xAcc
        accY = yAcc

        // Make sure we stay inside the field
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // TODO: implement the checkBoundaries function
        // Left wall
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }

        // Right wall
        val maxX = backgroundWidth - ballSize
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Top wall
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }

        // Bottom wall
        val maxY = backgroundHeight - ballSize
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // TODO: implement the reset function
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f

        isFirstUpdate = true
    }
}